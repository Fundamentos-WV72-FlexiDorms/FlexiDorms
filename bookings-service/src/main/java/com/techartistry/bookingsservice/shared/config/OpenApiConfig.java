package com.techartistry.bookingsservice.shared.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    String authServerUrl = "http://localhost:9090";
    String realm = "Flexidorms";
    private static final String OAUTH_SCHEME_NAME = "Flexidorms Keycloak OAuth2";

    @Bean
    public OpenAPI customOpenConfig() {
        return new OpenAPI()
            .info(new Info()
                .title("Booking management service")
                .description("Microservicio de gestión de reserva de FlexiDorms")
                .version("1.0.0")
            )
            .addSecurityItem(new SecurityRequirement()
                .addList(OAUTH_SCHEME_NAME)
            )
            .components(new Components()
                //OAUTH2 KEYCLOAK
                .addSecuritySchemes(OAUTH_SCHEME_NAME,
                    new SecurityScheme()
                        .type(SecurityScheme.Type.OAUTH2)
                        .description("Autorizar con Keycloak")
                        .flows(new OAuthFlows()
                            .implicit(new OAuthFlow()
                                .authorizationUrl(authServerUrl + "/realms/" + realm + "/protocol/openid-connect/auth")
//                                .scopes(new Scopes()
//                                    .addString("read_access", "read data")
//                                    .addString("write_access", "modify data")
//                                )
                            )
                        )
                )
            );
    }
}
