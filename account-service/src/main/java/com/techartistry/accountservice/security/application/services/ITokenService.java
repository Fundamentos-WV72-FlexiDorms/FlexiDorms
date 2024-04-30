package com.techartistry.accountservice.security.application.services;

import com.techartistry.accountservice.user.domain.entities.User;
import com.techartistry.accountservice.security.domain.model.entities.Token;
import com.techartistry.accountservice.security.domain.model.enums.ETokenType;

import java.util.Optional;

public interface ITokenService {
    /**
     * Genera un token de confirmación de email con 24 horas de duración y lo guarda en la base de datos
     * @param user Usuario al que se le generará el token
     * @return Token generado
     */
    Token generateEmailConfirmationToken(User user);

    /**
     * Genera un token de refresco de token de acceso con 7 días de duración y lo guarda en la base de datos
     * @param user Usuario al que se le generará el token
     * @return Token generado
     */
    Token generateRefreshToken(User user);

    /**
     * Verifica si un token de refresco ha expirado, si ha expirado lo elimina de la bd
     * @param token Token a verificar
     * @return Token verificado
     */
    Token verifyRefreshTokenExpiration(Token token);

    /**
     * Busca un token por el token y el tipo de token
     * @param token Token a buscar
     * @param tokenType Tipo de token a buscar
     * @return Token encontrado
     */
    Optional<Token> findByTokenAndTokenType(String token, ETokenType tokenType);

    /**
     * Revoca un token
     * @param tokenId ID del token a revocar
     * @param tokenType Tipo de token a revocar
     */
    void revokeToken(Long tokenId, ETokenType tokenType);
}
