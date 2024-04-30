package com.techartistry.accountservice.shared.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
