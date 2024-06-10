package com.techartistry.bookingsservice.bookings.application.clients;

import com.techartistry.bookingsservice.bookings.domain.entities.Room;
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
import java.util.Collections;

/**
 * Cliente Feign para el servicio de habitaciones (rooms-service)
 */
@FeignClient(name = "rooms-service", configuration = FeignClientConfig.class)
public interface IRoomClient {
    @GetMapping("/api/room/id/{roomId}")
    @CircuitBreaker(name = "account-cb", fallbackMethod = "fallbackGetRoomById")
    ResponseEntity<ApiResponse<Room>> getRoomById(@PathVariable Long roomId);

    /**
     * Método de fallback que se ejecuta cuando account-service no está disponible
     * @return Response de un Room por defecto
     */
    default ResponseEntity<ApiResponse<Room>> fallbackGetRoomById(Long roomId, Throwable throwable) {
        var defaultRoom = new Room();
        defaultRoom.setRoomId(roomId);
        defaultRoom.setName("None");
        defaultRoom.setAddress("None");
        defaultRoom.setAmenities(Collections.emptyList());
        defaultRoom.setRules(Collections.emptyList());
        defaultRoom.setPrice(0.0);
        defaultRoom.setImageUrl("none");
        defaultRoom.setCreatedAt(LocalDateTime.now());
        defaultRoom.setUpdatedAt(LocalDateTime.now());
        defaultRoom.setLessor(null);

        var fallbackResponse = new ApiResponse<>("Account service unavailable, showing fallback response", false, defaultRoom);
        return new ResponseEntity<>(fallbackResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Método de fallback que se ejecuta cuando account-service responde con un error
     * @return Lanza una excepción con el error recibido
     */
    default ResponseEntity<ApiResponse<Room>> fallbackGetRoomById(Long roomId, CustomException exception) {
        throw exception;
    }
}
