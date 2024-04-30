package com.techartistry.accountservice.security.application.dto.response;

import com.techartistry.accountservice.user.domain.enums.EGender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class LessorSignUpResponseDto {
    private Long userId;
    private String fullName;
    private String phoneNumber;
    private String dni;
    private LocalDate birthDate;
    private EGender gender;
    private String email;
    private String profilePicture;
    private boolean isVerified;

    public LessorSignUpResponseDto() {}
}