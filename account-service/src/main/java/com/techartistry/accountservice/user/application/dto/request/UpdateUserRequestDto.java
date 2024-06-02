package com.techartistry.accountservice.user.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateUserRequestDto {
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    private String username;

    @Email(message = "Email must be valid")
    private String email;

    @Size(min = 9, max = 9, message = "Phone number must be 9 characters")
    @Pattern(regexp = "\\d{9}", message = "Phone number must be 9 digits")
    private String phoneNumber;

    @Size(min = 8, max = 8, message = "DNI must be 8 characters")
    @Pattern(regexp = "\\d{8}", message = "DNI must be 8 digits")
    private String dni;

    private String gender;

    public UpdateUserRequestDto() {}
}
