package com.techartistry.accountservice.shared.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private String message;
    private boolean isSuccess;
    private T data;

    public ApiResponse(String message, boolean isSuccess, T data) {
        this.message = message;
        this.isSuccess = isSuccess;
        this.data = data;
    }
}
