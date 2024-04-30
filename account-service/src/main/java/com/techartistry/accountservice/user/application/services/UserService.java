package com.techartistry.accountservice.user.application.services;

import com.techartistry.accountservice.user.application.dto.request.*;
import com.techartistry.accountservice.user.application.dto.response.LessorUpdateResponseDto;
import com.techartistry.accountservice.user.application.dto.response.ProfileResponseDto;
import com.techartistry.accountservice.user.application.dto.response.StudentUpdateResponseDto;
import com.techartistry.accountservice.user.domain.enums.EGender;
import com.techartistry.accountservice.user.domain.entities.Lessor;
import com.techartistry.accountservice.user.domain.entities.Student;
import com.techartistry.accountservice.security.domain.model.UserPrincipal;
import com.techartistry.accountservice.shared.exception.CustomException;
import com.techartistry.accountservice.shared.exception.ResourceNotFoundException;
import com.techartistry.accountservice.user.infrastructure.repositories.IUserRepository;
import com.techartistry.accountservice.shared.model.dto.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(IUserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<ProfileResponseDto> getUserProfile(UserPrincipal userPrincipal) {
        var user = userRepository.findById(userPrincipal.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getUserId()));

        var res = modelMapper.map(user, ProfileResponseDto.class);
        return new ApiResponse<>("Ok", true, res);
    }

    @Override
    public ApiResponse<StudentUpdateResponseDto> updateStudent(UpdateStudentRequestDto request, Long studentId) {
        //1) se verifica si existe un usuario con el id dado
        var user = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", studentId));

        //se valida que sea un estudiante
        if (!(user instanceof Student)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Error", "The user is not a student");
        }

        //2) actualiza los datos
        user.setFullName(StringUtils.hasText(request.getFullName()) ? request.getFullName() : user.getFullName());
        user.setPhoneNumber(StringUtils.hasText(request.getPhoneNumber()) ? request.getPhoneNumber() : user.getPhoneNumber());
        user.setDni(StringUtils.hasText(request.getDni()) ? request.getDni() : user.getDni());
        user.setBirthDate(request.getBirthDate() != null ? request.getBirthDate() : user.getBirthDate());
        user.setGender(StringUtils.hasText(request.getGender()) ? EGender.valueOf(request.getGender()) : user.getGender());
        user.setProfilePicture(StringUtils.hasText(request.getProfilePicture()) ? request.getProfilePicture() : user.getProfilePicture());
        user.setEmail(StringUtils.hasText(request.getEmail()) ? request.getEmail() : user.getEmail());
        user.setPassword(StringUtils.hasText(request.getPassword()) ? request.getPassword() : user.getPassword());
        ((Student) user).setUniversity(StringUtils.hasText(request.getUniversity()) ? request.getUniversity() : ((Student) user).getUniversity());

        //3) se actualiza el usuario
        var studentUpdated = userRepository.save(user);

        //4) convertir el objeto de tipo User (entity) a un objeto de tipo StudentResponseDto (dto)
        var studentResponseDto = modelMapper.map(studentUpdated, StudentUpdateResponseDto.class);
        return new ApiResponse<>("Student updated successfully", true, studentResponseDto);
    }

    @Override
    public ApiResponse<LessorUpdateResponseDto> updateLessor(UpdateLessorRequestDto request, Long lessorId) {
        //1) se verifica si existe un usuario con el id dado
        var user = userRepository.findById(lessorId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", lessorId));

        //se valida que sea un arrendador
        if (!(user instanceof Lessor)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Error", "The user is not a lessor");
        }

        //2) actualiza los datos
        user.setFullName(StringUtils.hasText(request.getFullName()) ? request.getFullName() : user.getFullName());
        user.setPhoneNumber(StringUtils.hasText(request.getPhoneNumber()) ? request.getPhoneNumber() : user.getPhoneNumber());
        user.setDni(StringUtils.hasText(request.getDni()) ? request.getDni() : user.getDni());
        user.setBirthDate(request.getBirthDate() != null ? request.getBirthDate() : user.getBirthDate());
        user.setGender(StringUtils.hasText(request.getGender()) ? EGender.valueOf(request.getGender()) : user.getGender());
        user.setProfilePicture(StringUtils.hasText(request.getProfilePicture()) ? request.getProfilePicture() : user.getProfilePicture());
        user.setEmail(StringUtils.hasText(request.getEmail()) ? request.getEmail() : user.getEmail());
        user.setPassword(StringUtils.hasText(request.getPassword()) ? request.getPassword() : user.getPassword());

        //3) se actualiza el usuario
        var lessorUpdated = userRepository.save(user);

        //4) convertir el objeto de tipo User (entity) a un objeto de tipo LessorResponseDto (dto)
        var lessorResponseDto = modelMapper.map(lessorUpdated, LessorUpdateResponseDto.class);
        return new ApiResponse<>("Lessor updated successfully", true, lessorResponseDto);
    }
}
