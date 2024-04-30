package com.techartistry.accountservice.shared.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class CustomException extends RuntimeException {
    private HttpStatus httpStatus;
    private String details;

    public CustomException(HttpStatus httpStatus, String _message, String details) {
        super(_message);
        this.httpStatus = httpStatus;
        this.details = details;
    }
}
