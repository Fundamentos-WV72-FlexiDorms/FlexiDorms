package com.techartistry.accountservice.user.application.services;

import com.techartistry.accountservice.shared.exception.CustomException;
import com.techartistry.accountservice.shared.exception.ResourceNotFoundException;
import com.techartistry.accountservice.shared.model.dto.ApiResponse;
import com.techartistry.accountservice.user.application.dto.request.UpdateUserRequestDto;
import com.techartistry.accountservice.user.application.dto.response.UserProfileResponseDto;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.keycloak.admin.client.Keycloak;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserService implements IUserService {
    private final Keycloak keycloak;
    private final ModelMapper modelMapper;
    private final String REALM_NAME = "Flexidorms";

    public UserService(Keycloak keycloak, ModelMapper modelMapper) {
        this.keycloak = keycloak;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<UserProfileResponseDto> getUserById(String userId) {
        try {
            var userRepresentation = keycloak.realm(REALM_NAME)
                    .users()
                    .get(userId)
                    .toRepresentation();

            //mapea el objeto de tipo UserRepresentation a un objeto de tipo UserProfileResponseDto
            var res = modelMapper.map(userRepresentation, UserProfileResponseDto.class);
            return new ApiResponse<>("Ok", true, res);

        } catch (NotFoundException ex) {
            throw new ResourceNotFoundException("User", "id", userId);
        } catch (BadRequestException ex1) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Error while fetching user details. Please try again later.", ex1.getMessage());
        }
    }

    @Override
    public ApiResponse<List<UserProfileResponseDto>> getAllUsers() {
        var users = keycloak.realm(REALM_NAME)
                .users()
                .list();

        //mapea la lista de objetos de tipo UserRepresentation a una lista de objetos de tipo UserProfileResponseDto
        var res = users.stream()
                .map(userRepresentation -> modelMapper.map(userRepresentation, UserProfileResponseDto.class))
                .toList();
        return new ApiResponse<>("Ok", true, res);
    }

    @Override
    public ApiResponse<List<UserProfileResponseDto>> searchUserByUsername(String username) {
        var users = keycloak.realm(REALM_NAME)
                .users()
                .searchByUsername(username, true);

        var res = users.stream()
                .map(userRepresentation -> modelMapper.map(userRepresentation, UserProfileResponseDto.class))
                .toList();
        return new ApiResponse<>("Ok", true, res);
    }

    @Override
    public ApiResponse<List<UserProfileResponseDto>> searchUserByEmail(String email) {
        var users = keycloak.realm(REALM_NAME)
                .users()
                .searchByEmail(email, true);

        var res = users.stream()
                .map(userRepresentation -> modelMapper.map(userRepresentation, UserProfileResponseDto.class))
                .toList();
        return new ApiResponse<>("Ok", true, res);
    }

    @Override
    public ApiResponse<List<UserProfileResponseDto>> searchByCustomAttribute(String query) {
        var users = keycloak.realm(REALM_NAME)
                .users()
                .searchByAttributes(query);

        var res = users.stream()
                .map(userRepresentation -> modelMapper.map(userRepresentation, UserProfileResponseDto.class))
                .toList();
        return new ApiResponse<>("Ok", true, res);
    }

    @Override
    public ApiResponse<List<UserProfileResponseDto>> searchByRole(String roleName) {
        try {
            var users = keycloak.realm(REALM_NAME)
                    .roles()
                    .get(roleName)
                    .getUserMembers();

            var res = users.stream()
                    .map(userRepresentation -> modelMapper.map(userRepresentation, UserProfileResponseDto.class))
                    .toList();
            return new ApiResponse<>("Ok", true, res);

        } catch (NotFoundException ex) {
            throw new ResourceNotFoundException("Role", "name", roleName);
        } catch (BadRequestException ex1) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Error while fetching user details. Please try again later.", ex1.getMessage());
        }
    }

    public ApiResponse<Object> updateUser(UpdateUserRequestDto request, String userId) {
        try {
            //1) se obtiene el usuario
            var existingUser = keycloak.realm(REALM_NAME)
                    .users()
                    .get(userId)
                    .toRepresentation();

            //2) se actualizan los datos (atributos)
            var attributes = existingUser.getAttributes();
            attributes.put("phoneNumber", StringUtils.hasText(request.getPhoneNumber()) ? List.of(request.getPhoneNumber()) : attributes.get("phoneNumber"));
            attributes.put("dni", StringUtils.hasText(request.getDni()) ? List.of(request.getDni()) : attributes.get("dni"));
            attributes.put("gender", StringUtils.hasText(request.getGender()) ? List.of(request.getGender()) : attributes.get("gender"));
            //se actualizan los datos
            existingUser.setUsername(StringUtils.hasText(request.getUsername()) ? request.getUsername() : existingUser.getUsername());
            existingUser.setFirstName(StringUtils.hasText(request.getFirstName()) ? request.getFirstName() : existingUser.getFirstName());
            existingUser.setLastName(StringUtils.hasText(request.getLastName()) ? request.getLastName() : existingUser.getLastName());
            existingUser.setEmail(StringUtils.hasText(request.getEmail()) ? request.getEmail() : existingUser.getEmail());
            existingUser.setAttributes(attributes);

            //3) se actualiza el usuario
            keycloak.realm(REALM_NAME)
                    .users()
                    .get(userId)
                    .update(existingUser);
            return new ApiResponse<>("User updated successfully", true, null);

        } catch (NotFoundException ex) {
            throw new ResourceNotFoundException("User", "id", userId);
        } catch (BadRequestException ex1) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Error while fetching user details. Please try again later.", ex1.getMessage());
        }
    }

    @Override
    public ApiResponse<Object> deleteUserByUserId(String userId) {
        try {
            keycloak.realm(REALM_NAME)
                    .users()
                    .get(userId)
                    .remove();
            return new ApiResponse<>("User deleted successfully", true, null);

        } catch (NotFoundException ex) {
            throw new ResourceNotFoundException("User", "id", userId);
        } catch (BadRequestException ex1) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Error while fetching user details. Please try again later.", ex1.getMessage());
        }
    }
}
