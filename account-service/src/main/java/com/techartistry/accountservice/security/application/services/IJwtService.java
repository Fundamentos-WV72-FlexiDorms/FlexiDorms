package com.techartistry.accountservice.security.application.services;

import com.techartistry.accountservice.security.domain.model.UserPrincipal;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface IJwtService {
    /**
     * Genera el token JWT de acceso (con roles como claim)
     * @param userPrincipal Detalles del usuario autenticado
     * @return Token generado
     */
    String generateAccessToken(UserPrincipal userPrincipal);

    /**
     * Valida el token
     * @param token Token a validar
     * @param userDetails Detalles del usuario autenticado
     * @return True = token válido
     */
    boolean isTokenValid(String token, UserDetails userDetails);

    /**
     * Verifica si el token ha expirado
     * @param token Token a verificar
     * @return True si el token ha expirado, false en caso contrario
     */
    boolean isTokenExpired(String token);

    /**
     * Obtiene el subject (el email del usuario) a partir del token
     * @param token Token a procesar
     * @return Subject del token
     */
    String getSubject(String token);

    /**
     * Extrae un claim específico del token
     * @param token Token a procesar
     * @param claimsResolver Función que procesa los claims
     * @return Claim extraído
     * @param <T> Tipo de dato del claim
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}
