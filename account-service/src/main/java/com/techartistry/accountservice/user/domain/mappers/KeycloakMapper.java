package com.techartistry.accountservice.user.domain.mappers;

import com.techartistry.accountservice.user.application.dto.response.UserProfileResponseDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class KeycloakMapper {
    public KeycloakMapper() {}

    /**
     * Mapper personalizado para mapear un UserRepresentation (Keycloak) a un UserProfileResponseDto
     */
    public static Converter<UserRepresentation, UserProfileResponseDto> userRepresentationToUserProfileResponseDto() {
        return (context) -> {
            var dto = new UserProfileResponseDto();
            var source = context.getSource();

            dto.setId(source.getId());
            dto.setUsername(source.getUsername());
            dto.setFirstName(source.getFirstName());
            dto.setLastName(source.getLastName());
            dto.setEmail(source.getEmail());
            dto.setEmailVerified(source.isEmailVerified());
            dto.setEnabled(source.isEnabled());
            dto.setCreatedTimestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(source.getCreatedTimestamp()), ZoneId.systemDefault()));

            //campo attributes de UserRepresentation
            dto.setPhoneNumber((String) ((List<?>) source.getAttributes().get("phoneNumber")).get(0));
            dto.setDni((String) ((List<?>) source.getAttributes().get("dni")).get(0));
            dto.setGender((String) ((List<?>) source.getAttributes().get("gender")).get(0));

            return dto;
        };
    }
}
