package com.techartistry.gatewayservice.controllers;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@RestController
public class GatewayController {
    /**
     * Obtiene el id de la sesión
     * @param session Sesión
     */
    @GetMapping("/")
    public Mono<String> index(WebSession session) {
        return Mono.just(session.getId());
    }

    /**
     * Obtiene el token de acceso
     * @param authorizedClient Cliente autorizado
     */
    @GetMapping("/token")
    public Mono<String> getToken(
            @RegisteredOAuth2AuthorizedClient
            OAuth2AuthorizedClient authorizedClient
    ) {
        return Mono.just(authorizedClient.getAccessToken().getTokenValue());
    }
}
