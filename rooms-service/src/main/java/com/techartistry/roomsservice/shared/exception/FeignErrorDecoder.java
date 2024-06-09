package com.techartistry.roomsservice.shared.exception;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techartistry.roomsservice.shared.dto.ApiResponse;
import com.techartistry.roomsservice.shared.exception.dto.ErrorMessageResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * Decodificador de errores para mapear los errores de Feign a excepciones personalizadas
 */
public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ApiResponse<ErrorMessageResponse> errorResponse;
        var responseBody = response.body();
        var responseStatus = HttpStatus.valueOf(response.status());

        //se intenta mapear el cuerpo de la respuesta a un objeto de tipo ApiResponse<ErrorMessageResponse> (que es el que devuelve el cliente)
        try (var responseBodyInputStream = responseBody.asInputStream()) {
            var mapper = new ObjectMapper();
            errorResponse = mapper.readValue(responseBodyInputStream, new TypeReference<>() { });
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }

        //se retorna la excepción personalizada dependiendo del código de estado de la respuesta
        return switch (responseStatus) {
            case BAD_REQUEST -> new CustomException(HttpStatus.BAD_REQUEST, "Bad request", errorResponse.getData().getDetails());
            case NOT_FOUND -> new CustomException(HttpStatus.NOT_FOUND, "Resource not found", errorResponse.getData().getDetails());
            default -> defaultErrorDecoder.decode(methodKey, response);
        };
    }
}
