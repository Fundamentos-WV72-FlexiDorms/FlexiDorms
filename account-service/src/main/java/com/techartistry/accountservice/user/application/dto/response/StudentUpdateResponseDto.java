package com.techartistry.accountservice.user.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentUpdateResponseDto extends LessorUpdateResponseDto {
    private String university;

    public StudentUpdateResponseDto() {}
}