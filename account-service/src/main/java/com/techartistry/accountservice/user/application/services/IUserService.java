package com.techartistry.accountservice.user.application.services;

import com.techartistry.accountservice.shared.model.dto.ApiResponse;
import com.techartistry.accountservice.user.application.dto.request.UpdateUserRequestDto;
import com.techartistry.accountservice.user.application.dto.response.UserProfileResponseDto;

import java.util.List;

public interface IUserService {
    /**
     * Obtiene un usuario por su id
     * @param userId El id del usuario
     */
    ApiResponse<UserProfileResponseDto> getUserById(String userId);

    /**
     * Obtiene todos los usuarios
     * @return Lista de usuarios de keycloak
     */
    ApiResponse<List<UserProfileResponseDto>> getAllUsers();

    /**
     * Busca un usuario por su username
     * @param username El username del usuario
     * @return Usuario de keycloak
     */
    ApiResponse<List<UserProfileResponseDto>> searchUserByUsername(String username);

    /**
     * Busca un usuario por su email
     * @param email El email del usuario
     * @return Usuario de keycloak
     */
    ApiResponse<List<UserProfileResponseDto>> searchUserByEmail(String email);

    /**
     * Busca un usuario por un atributo personalizado
     * @param query El query de búsqueda (atributo:valor)
     * @return Usuario de keycloak
     */
    ApiResponse<List<UserProfileResponseDto>> searchByCustomAttribute(String query);

    /**
     * Busca un usuario por su rol
     * @param roleName El nombre del rol
     * @return Usuario de keycloak
     */
    ApiResponse<List<UserProfileResponseDto>> searchByRole(String roleName);

    /**
     * Actualiza un usuario
     * @param request Datos del usuario a actualizar
     * @param userId El id del usuario
     */
    ApiResponse<Object> updateUser(UpdateUserRequestDto request, String userId);

    /**
     * Elimina un usuario
     * @param userId El id del usuario
     */
    ApiResponse<Object> deleteUserByUserId(String userId);
}
