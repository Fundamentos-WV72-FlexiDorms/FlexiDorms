package com.techartistry.accountservice.user.application.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStudentRequestDto extends UpdateLessorRequestDto {
    @Size(min = 2, max = 50, message = "University must be between 2 and 50 characters")
    private String university;

    public UpdateStudentRequestDto() {}
}
