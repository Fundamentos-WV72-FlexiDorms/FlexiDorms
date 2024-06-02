package com.techartistry.accountservice.user.application.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDto {
    @Size(min = 3, message = "New password must be at least 3 characters")
    private String password;

    @Size(min = 3, message = "Confirm password must be at least 3 characters")
    private String confirmPassword;

    public ChangePasswordRequestDto() {}
}
