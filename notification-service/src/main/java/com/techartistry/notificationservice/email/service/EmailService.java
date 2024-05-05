package com.techartistry.notificationservice.email.service;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    @Override
    public void sendVerificationEmail(String user, String userEmail, String token) {
        //se crea el mensaje, se establece el enlace de confirmación y se envía
        var context = new Context();
        context.setVariable("verificationUrl", "http://localhost:8080/account-service/api/auth/verify-account?token=" + token);
        context.setVariable("user", user);

        //se procesa la plantilla
        String process = templateEngine.process("verification_email", context);

        //se envía el correo
        try {
            var mimeMessage = javaMailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(process, true);
            helper.setTo(userEmail);
            helper.setSubject("FlexiDorms - ¡Verifica tu cuenta!");
            javaMailSender.send(mimeMessage);
            log.info("Email sent to -> " + userEmail);
        } catch (MessagingException ex) {
            log.error("Error during email sending -> " + ex.getMessage());
        }
    }
}
