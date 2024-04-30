package com.techartistry.accountservice.security.application.dto.response;

public record SignInResponseDto(
        String accessToken,
        String refreshToken
) {}
