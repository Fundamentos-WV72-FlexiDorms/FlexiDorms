package com.techartistry.gatewayservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    /**
     * Intercepta todas las solicitudes entrantes y asegura que el usuario esté autenticado.
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, ServerLogoutSuccessHandler logoutSuccessHandler) {
        http.authorizeExchange(exchanges -> exchanges
            .anyExchange().authenticated()
        );
        http.oauth2Login(Customizer.withDefaults());
        http.logout(logout -> logout
            .logoutSuccessHandler(logoutSuccessHandler)
        );
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }

    /**
     * Se crea un controlador de logout del servidor iniciado por el cliente.
     * Esto inicia el cierre de sesión del usuario en el servidor de autorización (Keycloak).
     * @param clientRegistrationRepository Repositorio de registro de clientes
     */
    @Bean
    public ServerLogoutSuccessHandler logoutSuccessHandler(ReactiveClientRegistrationRepository clientRegistrationRepository) {
        var successHandler = new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);
        successHandler.setPostLogoutRedirectUri("{baseUrl}/login?logout");
        return successHandler;
    }
}
