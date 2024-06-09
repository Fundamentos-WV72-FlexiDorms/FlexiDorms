package com.techartistry.accountservice.shared.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponse {
    private String details;
    private String uri;
    private LocalDateTime timestamp;
}
