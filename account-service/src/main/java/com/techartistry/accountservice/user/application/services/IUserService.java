package com.techartistry.accountservice.user.application.services;

import com.techartistry.accountservice.user.application.dto.request.*;
import com.techartistry.accountservice.user.application.dto.response.LessorUpdateResponseDto;
import com.techartistry.accountservice.user.application.dto.response.ProfileResponseDto;
import com.techartistry.accountservice.user.application.dto.response.StudentUpdateResponseDto;
import com.techartistry.accountservice.security.domain.model.UserPrincipal;
import com.techartistry.accountservice.shared.model.dto.ApiResponse;

public interface IUserService {
    /**
     * Obtiene el perfil de un usuario
     * @param userPrincipal Usuario autenticado
     */
    ApiResponse<ProfileResponseDto> getUserProfile(UserPrincipal userPrincipal);

    /**
     * Actualiza un estudiante
     * @param request El estudiante a actualizar
     * @param studentId El id del estudiante
     */
    ApiResponse<StudentUpdateResponseDto> updateStudent(UpdateStudentRequestDto request, Long studentId);

    /**
     * Actualiza un arrendador
     * @param request El arrendador a actualizar
     * @param lessorId El id del arrendador
     */
    ApiResponse<LessorUpdateResponseDto> updateLessor(UpdateLessorRequestDto request, Long lessorId);
}
