package com.techartistry.accountservice.user.application.services;

import com.techartistry.accountservice.shared.model.dto.ApiResponse;
import com.techartistry.accountservice.user.application.dto.request.UpdateUserRequestDto;
import com.techartistry.accountservice.user.application.dto.response.UserProfileResponseDto;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
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
        var userRepresentation = keycloak.realm(REALM_NAME)
                .users()
                .get(userId)
                .toRepresentation();

        //mapea el objeto de tipo UserRepresentation a un objeto de tipo UserProfileResponseDto
        var res = modelMapper.map(userRepresentation, UserProfileResponseDto.class);
        return new ApiResponse<>("Ok", true, res);
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
        var users = keycloak.realm(REALM_NAME)
                .roles()
                .get(roleName)
                .getUserMembers();

        var res = users.stream()
                .map(userRepresentation -> modelMapper.map(userRepresentation, UserProfileResponseDto.class))
                .toList();
        return new ApiResponse<>("Ok", true, res);
    }

    public ApiResponse<Object> updateUser(UpdateUserRequestDto request, String userId) {
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
    }

    @Override
    public ApiResponse<Object> deleteUserByUserId(String userId) {
        keycloak.realm(REALM_NAME)
                .users()
                .get(userId)
                .remove();
        return new ApiResponse<>("User deleted successfully", true, null);
    }
}
