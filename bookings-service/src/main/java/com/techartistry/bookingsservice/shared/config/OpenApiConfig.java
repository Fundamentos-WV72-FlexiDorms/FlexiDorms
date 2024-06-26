package com.techartistry.bookingsservice.shared.config;

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
                .title("Booking management service")
                .description("Microservicio de gestión de reserva de FlexiDorms")
                .version("1.0.0")
            )
            .addSecurityItem(new SecurityRequirement()
                .addList("OIDC")
            )
            .components(new Components()
                .addSecuritySchemes("OIDC",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.OPENIDCONNECT)
                        .description("Autorizar con Keycloak (OIDC)")
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .openIdConnectUrl("http://localhost:9090/realms/Flexidorms/.well-known/openid-configuration")
                )
            );
    }
}
