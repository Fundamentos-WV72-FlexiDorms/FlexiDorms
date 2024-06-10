package com.techartistry.accountservice.shared.config;

import com.techartistry.accountservice.user.application.dto.response.UserProfileResponseDto;
import com.techartistry.accountservice.user.domain.mappers.KeycloakMapper;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        //custom mappings
        modelMapper.createTypeMap(UserRepresentation.class, UserProfileResponseDto.class)
                .setConverter(KeycloakMapper.userRepresentationToUserProfileResponseDto());

        return modelMapper;
    }
}
