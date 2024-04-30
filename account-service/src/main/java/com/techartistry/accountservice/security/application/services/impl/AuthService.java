package com.techartistry.accountservice.security.application.services.impl;

import com.techartistry.accountservice.user.domain.entities.Lessor;
import com.techartistry.accountservice.user.domain.entities.Student;
import com.techartistry.accountservice.user.domain.enums.ERole;
import com.techartistry.accountservice.user.infrastructure.repositories.IRoleRepository;
import com.techartistry.accountservice.user.infrastructure.repositories.IUserRepository;
import com.techartistry.accountservice.security.application.dto.request.RefreshTokenRequestDto;
import com.techartistry.accountservice.security.application.dto.request.SignInUserRequestDto;
import com.techartistry.accountservice.security.application.dto.request.SignUpLessorRequestDto;
import com.techartistry.accountservice.security.application.dto.request.SignUpStudentRequestDto;
import com.techartistry.accountservice.security.application.dto.response.LessorSignUpResponseDto;
import com.techartistry.accountservice.security.application.dto.response.RefreshTokenResponseDto;
import com.techartistry.accountservice.security.application.dto.response.SignInResponseDto;
import com.techartistry.accountservice.security.application.dto.response.StudentSignUpResponseDto;
import com.techartistry.accountservice.security.application.services.IAuthService;
import com.techartistry.accountservice.security.application.services.IJwtService;
import com.techartistry.accountservice.security.application.services.ITokenService;
import com.techartistry.accountservice.security.domain.model.UserPrincipal;
import com.techartistry.accountservice.security.domain.model.enums.ETokenType;
import com.techartistry.accountservice.shared.exception.CustomException;
import com.techartistry.accountservice.shared.exception.ResourceNotFoundException;
import com.techartistry.accountservice.shared.model.dto.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService implements IAuthService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final IJwtService jwtService;
    private final ITokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(IUserRepository userRepository, IRoleRepository roleRepository, ModelMapper modelMapper, IJwtService jwtService, ITokenService tokenService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ApiResponse<StudentSignUpResponseDto> signUpStudent(SignUpStudentRequestDto request) {
        //validar que el email y el número de celular no están registrados
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Error", "The email given is already registered");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw  new CustomException(HttpStatus.BAD_REQUEST, "Error", "The phone number given is already registered");
        }

        //encriptar la contraseña desde el request
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        //convertir el request (dto) a un objeto de tipo User (entity)
        var student = modelMapper.map(request, Student.class);

        //establecer los roles
        var roles = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Error", "Role not found"));
        student.setRoles(Collections.singleton(roles)); //establece un solo rol

        //guarda el Student
        var studentCreated = userRepository.save(student);

        //convertir el objeto de tipo User (entity) a un objeto de tipo StudentResponseDto (dto)
        var studentResponseDto = modelMapper.map(studentCreated, StudentSignUpResponseDto.class);

        return new ApiResponse<>("Student was successfully registered", true, studentResponseDto);
    }

    @Override
    public ApiResponse<LessorSignUpResponseDto> signUpLessor(SignUpLessorRequestDto request) {
        //validar que el email y el número de celular no están registrados
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Error", "The email given is already registered");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw  new CustomException(HttpStatus.BAD_REQUEST, "Error", "The phone number given is already registered");
        }

        //encriptar la contraseña desde el request
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        //convertir el request (dto) a un objeto de tipo User (entity)
        var lessor = modelMapper.map(request, Lessor.class);

        //establecer los roles
        var roles = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Error", "Role not found"));
        lessor.setRoles(Collections.singleton(roles)); //establece un solo rol

        //guarda el Student
        var lessorCreated = userRepository.save(lessor);

        //convertir el objeto de tipo User (entity) a un objeto de tipo StudentResponseDto (dto)
        var lessorResponseDto = modelMapper.map(lessorCreated, LessorSignUpResponseDto.class);

        return new ApiResponse<>("Lessor was successfully registered", true, lessorResponseDto);
    }

    @Override
    public ApiResponse<SignInResponseDto> signIn(SignInUserRequestDto request) {
        //se intenta autenticar
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        //genera el token de acceso
        var accessToken = jwtService.generateAccessToken((UserPrincipal) authentication.getPrincipal());

        //genera el token de refresco (uno por cada sesión, ya que se contempla el escenario de sesiones múltiples)
        var refreshToken = tokenService.generateRefreshToken(user);

        var responseData = new SignInResponseDto(accessToken, refreshToken.getToken());
        return new ApiResponse<>("Authentication successful", true, responseData);
    }

    @Override
    public ApiResponse<RefreshTokenResponseDto> refreshToken(RefreshTokenRequestDto request) {
        var refreshToken = tokenService.findByTokenAndTokenType(request.refreshToken(), ETokenType.REFRESH_TOKEN)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Error al renovar el token", "Token de refresco no encontrado o inválido"));

        //verifica si el token de refresco ha expirado
        var verifiedRefreshToken = tokenService.verifyRefreshTokenExpiration(refreshToken);

        //si el token de refresco es válido, genera un nuevo token de acceso
        var user = verifiedRefreshToken.getUser();
        var accessToken = jwtService.generateAccessToken(new UserPrincipal(user));

        var response = new RefreshTokenResponseDto(accessToken);
        return new ApiResponse<>("Access token renewed successfully", true, response);
    }
}
