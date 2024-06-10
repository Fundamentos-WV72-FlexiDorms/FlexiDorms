package com.techartistry.roomsservice.rooms.application.clients;

import com.techartistry.roomsservice.rooms.domain.entities.Lessor;
import com.techartistry.roomsservice.shared.config.FeignClientConfig;
import com.techartistry.roomsservice.shared.dto.ApiResponse;
import com.techartistry.roomsservice.shared.exception.CustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

/**
 * Cliente Feign para el servicio de cuentas (account-service)
 */
@FeignClient(name = "account-service", configuration = FeignClientConfig.class)
public interface ILessorClient {
    @GetMapping("/api/user/id/{userId}")
    @CircuitBreaker(name = "account-cb", fallbackMethod = "fallbackGetLessorById")
    ResponseEntity<ApiResponse<Lessor>> getLessorById(@PathVariable String userId);

    /**
     * Método de fallback que se ejecuta cuando account-service no está disponible
     * @return Response de un Lessor por defecto
     */
    default ResponseEntity<ApiResponse<Lessor>> fallbackGetLessorById(String userId, Throwable throwable) {
        var defaultLessor = new Lessor();
        defaultLessor.setId(userId);
        defaultLessor.setUsername("None");
        defaultLessor.setFirstName("None");
        defaultLessor.setLastName("None");
        defaultLessor.setEmail("none@email.com");
        defaultLessor.setPhoneNumber("000000000");
        defaultLessor.setDni("00000000A");
        defaultLessor.setGender("None");
        defaultLessor.setEmailVerified(false);
        defaultLessor.setEnabled(false);
        defaultLessor.setCreatedTimestamp(LocalDateTime.now());

        var fallbackResponse = new ApiResponse<>("Account service unavailable, showing fallback response", false, defaultLessor);
        return new ResponseEntity<>(fallbackResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Método de fallback que se ejecuta cuando account-service responde con un error
     * @return Lanza una excepción con el error recibido
     */
    default ResponseEntity<ApiResponse<Lessor>> fallbackGetLessorById(String userId, CustomException exception) {
        throw exception;
    }
}
