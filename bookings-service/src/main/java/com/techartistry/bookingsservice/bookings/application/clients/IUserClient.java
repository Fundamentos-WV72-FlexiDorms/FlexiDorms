package com.techartistry.bookingsservice.bookings.application.clients;

import com.techartistry.bookingsservice.bookings.domain.entities.User;
import com.techartistry.bookingsservice.shared.config.FeignClientConfig;
import com.techartistry.bookingsservice.shared.dto.ApiResponse;
import com.techartistry.bookingsservice.shared.exception.CustomException;
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
public interface IUserClient {
    @GetMapping("/api/user/id/{userId}")
    @CircuitBreaker(name = "account-cb", fallbackMethod = "fallbackGetUserById")
    ResponseEntity<ApiResponse<User>> getUserById(@PathVariable String userId);

    /**
     * Método de fallback que se ejecuta cuando account-service no está disponible
     * @return Response de un User por defecto
     */
    default ResponseEntity<ApiResponse<User>> fallbackGetUserById(String userId, Throwable throwable) {
        var defaultUser = new User();
        defaultUser.setId(userId);
        defaultUser.setUsername("None");
        defaultUser.setFirstName("None");
        defaultUser.setLastName("None");
        defaultUser.setEmail("none@email.com");
        defaultUser.setPhoneNumber("000000000");
        defaultUser.setDni("00000000A");
        defaultUser.setGender("None");
        defaultUser.setEmailVerified(false);
        defaultUser.setEnabled(false);
        defaultUser.setCreatedTimestamp(LocalDateTime.now());

        var fallbackResponse = new ApiResponse<>("Account service unavailable, showing fallback response", false, defaultUser);
        return new ResponseEntity<>(fallbackResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Método de fallback que se ejecuta cuando account-service responde con un error
     * @return Lanza una excepción con el error recibido
     */
    default ResponseEntity<ApiResponse<User>> fallbackGetUserById(String userId, CustomException exception) {
        throw exception;
    }
}
