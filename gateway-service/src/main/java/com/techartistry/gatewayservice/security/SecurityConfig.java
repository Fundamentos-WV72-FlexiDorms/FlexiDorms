package com.techartistry.gatewayservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    /**
     * Intercepta todas las solicitudes entrantes y asegura que el usuario esté autenticado.
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(exchanges -> exchanges
            .anyExchange().authenticated()
        );
        http.oauth2Login(Customizer.withDefaults());
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }
}
