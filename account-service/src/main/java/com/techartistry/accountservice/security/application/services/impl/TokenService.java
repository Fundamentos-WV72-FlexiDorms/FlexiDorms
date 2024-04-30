package com.techartistry.accountservice.security.application.services.impl;

import com.techartistry.accountservice.user.domain.entities.User;
import com.techartistry.accountservice.security.application.services.ITokenService;
import com.techartistry.accountservice.security.domain.model.entities.Token;
import com.techartistry.accountservice.security.domain.model.enums.ETokenType;
import com.techartistry.accountservice.security.infrastructure.repositories.ITokenRepository;
import com.techartistry.accountservice.shared.exception.CustomException;
import com.techartistry.accountservice.shared.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService implements ITokenService {
    private final ITokenRepository tokenRepository;

    public TokenService(ITokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    @Override
    public Token generateEmailConfirmationToken(User user) {
        //se eliminan todos los tokens de confirmación de email sin usar del usuario (solo puede haber uno)
        tokenRepository.deleteAllByUserUserIdAndIsRevokedIsFalseAndTokenType(user.getUserId(), ETokenType.EMAIL_CONFIRMATION);

        var confirmationToken = new Token();
        confirmationToken.setUser(user);
        confirmationToken.setTokenType(ETokenType.EMAIL_CONFIRMATION);
        confirmationToken.setToken(UUID.randomUUID().toString());
        confirmationToken.setExpirationDate(24);

        return tokenRepository.save(confirmationToken);
    }

    @Transactional
    @Override
    public Token generateRefreshToken(User user) {
        var refreshToken = new Token();
        refreshToken.setUser(user);
        refreshToken.setTokenType(ETokenType.REFRESH_TOKEN);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpirationDate(24 * 7); //7 dias de duración

        return tokenRepository.save(refreshToken);
    }

    @Transactional
    @Override
    public Token verifyRefreshTokenExpiration(Token token) {
        //si el token de refresco ha expirado, se elimina y se lanza una excepción
        if (token.isExpired()) {
            tokenRepository.delete(token);
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Token expired", "The refresh token has expired, please sign in again");
        }

        return token;
    }

    @Transactional
    @Override
    public Optional<Token> findByTokenAndTokenType(String token, ETokenType tokenType) {
        return tokenRepository.findByTokenAndTokenType(token, tokenType);
    }

    @Transactional
    @Override
    public void revokeToken(Long tokenId, ETokenType tokenType) {
        var token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token", "id", tokenId));
        token.setRevoked(true);
        tokenRepository.save(token);
    }
}
