package com.techartistry.notificationservice.email.service;

public interface IEmailService {
    /**
     * Envía el email de verificación de la cuenta
     * @param user Nombres del usuario
     * @param userEmail Email del usuario
     * @param token Token de confirmación
     */
    void sendVerificationEmail(String user, String userEmail, String token);
}
