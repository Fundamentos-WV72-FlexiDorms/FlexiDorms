package com.techartistry.accountservice.security.domain.model.entities;

import com.techartistry.accountservice.user.domain.entities.User;
import com.techartistry.accountservice.security.domain.model.enums.ETokenType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long tokenId;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false)
    private ETokenType tokenType;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "is_revoked", columnDefinition = "boolean default false")
    private boolean isRevoked;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAtn;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    /**
     * MUCHOS tokens pueden tener UN usuario
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Token() {}

    /**
     * Asigna el tiempo de expiración del token.
     * @param expirationDurationInHours Duración en horas hasta la expiración del token.
     */
    public void setExpirationDate(int expirationDurationInHours) {
        this.expirationDate = LocalDateTime.now().plusHours(expirationDurationInHours);
    }

    /**
     * Verifica si el token ha expirado
     * @return true si el token ha expirado, false de lo contrario
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationDate);
    }
}