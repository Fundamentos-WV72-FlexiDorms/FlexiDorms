package com.techartistry.accountservice.user.application.dto.request;

import jakarta.validation.constraints.Size;

public record ChangePasswordRequestDto(
    @Size(min = 3, message = "New password must be at least 3 characters")
    String newPassword,
    @Size(min = 3, message = "Confirm password must be at least 3 characters")
    String confirmNewPassword
) {}
