package com.techartistry.accountservice.security.domain.events;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa los datos del evento de usuarios registrados
 */
@Setter
@Getter
public class UserSignedUpEventData {
    private Long userId;
    private String fullName;
    private String email;
    private String token;

    public UserSignedUpEventData() {}

    public UserSignedUpEventData(Long userId, String fullName, String email, String token) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.token = token;
    }
}