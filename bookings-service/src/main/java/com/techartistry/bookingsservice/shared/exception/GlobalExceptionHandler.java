package com.techartistry.bookingsservice.shared.exception;

import com.techartistry.bookingsservice.shared.dto.ApiResponse;
import com.techartistry.bookingsservice.shared.exception.dto.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiResponse<ErrorMessageResponse> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest
    ) {
        var errorMessage = new ErrorMessageResponse(
                exception.getMessage(),
                webRequest.getDescription(false),
                new Date()
        );

        return new ApiResponse<>("Resource not found", false, errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ApiResponse<List<String>> handleValidationErrors(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> {
                    var fieldName = fieldError.getField();
                    var errorMessage = fieldError.getDefaultMessage();
                    return String.format("%s: %s", fieldName, errorMessage);
                })
                .toList();

        return new ApiResponse<>("Validation error", false, errors);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<ErrorMessageResponse> handleGlobalException(
            Exception exception,
            WebRequest webRequest
    ) {
        var errorMessage = new ErrorMessageResponse(
                exception.getMessage(),
                webRequest.getDescription(false),
                new Date()
        );

        return new ApiResponse<>("An unexpected error has occurred", false, errorMessage);
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(value = HttpStatus.FORBIDDEN)
//    public ApiResponse<ErrorMessageResponse> handleAccessDeniedException(
//            AccessDeniedException exception,
//            WebRequest webRequest
//    ) {
//        var errorMessage = new ErrorMessageResponse(
//                new Date(),
//                exception.getMessage(),
//                webRequest.getDescription(false)
//        );
//
//        return new ApiResponse<>("Access denied", false, errorMessage);
//    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<ErrorMessageResponse>> handleAppException(
            CustomException exception,
            WebRequest webRequest
    ) {
        var errorMessage = new ErrorMessageResponse(
                exception.getDetails(),
                webRequest.getDescription(false),
                new Date()
        );

        var response = new ApiResponse<>(exception.getMessage(), false, errorMessage);
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }
}
