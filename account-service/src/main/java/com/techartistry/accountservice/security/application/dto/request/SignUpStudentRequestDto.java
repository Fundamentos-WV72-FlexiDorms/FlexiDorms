package com.techartistry.accountservice.security.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpStudentRequestDto extends SignUpLessorRequestDto {
    @NotBlank(message = "University is required")
    private String university;

    public SignUpStudentRequestDto() {}
}
