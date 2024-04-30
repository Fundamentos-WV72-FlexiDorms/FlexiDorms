package com.techartistry.accountservice.security.application.services;

import com.techartistry.accountservice.security.application.dto.request.RefreshTokenRequestDto;
import com.techartistry.accountservice.security.application.dto.request.SignInUserRequestDto;
import com.techartistry.accountservice.security.application.dto.request.SignUpLessorRequestDto;
import com.techartistry.accountservice.security.application.dto.request.SignUpStudentRequestDto;
import com.techartistry.accountservice.security.application.dto.response.LessorSignUpResponseDto;
import com.techartistry.accountservice.security.application.dto.response.RefreshTokenResponseDto;
import com.techartistry.accountservice.security.application.dto.response.SignInResponseDto;
import com.techartistry.accountservice.security.application.dto.response.StudentSignUpResponseDto;
import com.techartistry.accountservice.shared.model.dto.ApiResponse;

public interface IAuthService {
    /**
     * Registra un estudiante
     * @param request El estudiante a registrar
     * @return El estudiante registrado
     */
    ApiResponse<StudentSignUpResponseDto> signUpStudent(SignUpStudentRequestDto request);

    /**
     * Registra un arrendador
     * @param request El arrendador a registrar
     * @return El arrendador registrado
     */
    ApiResponse<LessorSignUpResponseDto> signUpLessor(SignUpLessorRequestDto request);

    /**
     * Inicia sesión
     * @param request El usuario a iniciar sesión
     */
    ApiResponse<SignInResponseDto> signIn(SignInUserRequestDto request);

    /**
     * Renueva el token de acceso con un token de refresco
     * @param request Token de refresco
     * @return Token de acceso renovado
     */
    ApiResponse<RefreshTokenResponseDto> refreshToken(RefreshTokenRequestDto request);
}
