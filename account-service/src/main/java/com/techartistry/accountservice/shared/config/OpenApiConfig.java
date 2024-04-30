package com.techartistry.accountservice.shared.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenConfig() {
        return new OpenAPI()
            .info(new Info()
                .title("UserAuth Service")
                .description("Microservicio de gestión de usuarios y autenticación de FlexiDorms")
                .version("1.0.0")
            )
            .addSecurityItem(new SecurityRequirement()
                .addList("JwtScheme")
            )
            .components(new Components()
                //JWT
                .addSecuritySchemes("JwtScheme",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .description("Autorizar por un token JWT")
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            );
    }
}
