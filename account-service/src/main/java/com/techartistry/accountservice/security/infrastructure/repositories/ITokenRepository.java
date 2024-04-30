package com.techartistry.accountservice.security.infrastructure.repositories;

import com.techartistry.accountservice.security.domain.model.entities.Token;
import com.techartistry.accountservice.security.domain.model.enums.ETokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITokenRepository extends JpaRepository<Token, Long> {
    /**
     * Busca un token de confirmación por el token
     * @param token Token a buscar
     */
    Optional<Token> findByTokenAndTokenType(String token, ETokenType tokenType);

    /**
     * Elimina todos los tokens de un usuario que no hayan sido usados
     * @param userId Id del usuario
     * @param tokenType Tipo de token a eliminar
     */
    void deleteAllByUserUserIdAndIsRevokedIsFalseAndTokenType(Long userId, ETokenType tokenType);
}
