package com.techartistry.accountservice.user.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateLessorRequestDto {
    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    private String fullName;

    @Size(min = 9, max = 9, message = "Phone number must be 9 characters")
    @Pattern(regexp = "\\d{9}", message = "Phone number must be 9 digits")
    private String phoneNumber;

    @Size(min = 8, max = 8, message = "DNI must be 8 characters")
    @Pattern(regexp = "\\d{8}", message = "DNI must be 8 digits")
    private String dni;

    @Email(message = "Email must be valid")
    private String email;

    @Size(min = 3, message = "Password must be at least 3 characters")
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String profilePicture;
    private String gender;

    public UpdateLessorRequestDto() {}
}
