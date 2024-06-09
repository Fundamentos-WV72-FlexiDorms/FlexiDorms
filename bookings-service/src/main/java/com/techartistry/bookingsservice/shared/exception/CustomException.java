package com.techartistry.bookingsservice.shared.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Setter
@Getter
public class CustomException extends RuntimeException {
    private HttpStatusCode httpStatus;
    private String details;

    public CustomException(HttpStatus httpStatus, String _message, String details) {
        super(_message);
        this.httpStatus = httpStatus;
        this.details = details;
    }

    public CustomException(HttpStatusCode httpStatus, String _message, String details) {
        super(_message);
        this.httpStatus = httpStatus;
        this.details = details;
    }
}
