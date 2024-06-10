package com.techartistry.accountservice.user.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserProfileResponseDto {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dni;
    private String gender;
    private boolean emailVerified;
    private boolean enabled;
    private LocalDateTime createdTimestamp;

    public UserProfileResponseDto() {}
}