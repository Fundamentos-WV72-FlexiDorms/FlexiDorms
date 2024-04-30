package com.techartistry.accountservice.security.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentSignUpResponseDto extends LessorSignUpResponseDto {
    private String university;

    public StudentSignUpResponseDto() {}
}