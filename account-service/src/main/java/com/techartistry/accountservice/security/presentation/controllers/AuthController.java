package com.techartistry.accountservice.security.presentation.controllers;

import com.techartistry.accountservice.security.application.dto.request.RefreshTokenRequestDto;
import com.techartistry.accountservice.security.application.dto.request.SignInUserRequestDto;
import com.techartistry.accountservice.security.application.dto.request.SignUpLessorRequestDto;
import com.techartistry.accountservice.security.application.dto.request.SignUpStudentRequestDto;
import com.techartistry.accountservice.security.application.dto.response.LessorSignUpResponseDto;
import com.techartistry.accountservice.security.application.dto.response.RefreshTokenResponseDto;
import com.techartistry.accountservice.security.application.dto.response.SignInResponseDto;
import com.techartistry.accountservice.security.application.dto.response.StudentSignUpResponseDto;
import com.techartistry.accountservice.security.application.services.IAuthService;
import com.techartistry.accountservice.shared.model.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirements //desactiva la seguridad para este controlador
@Tag(name = "Auth")
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final IAuthService service;

    public AuthController(IAuthService service) {
        this.service = service;
    }

    @Operation(summary = "Sign up a student")
    @PostMapping("/signUp/student")
    public ResponseEntity<ApiResponse<StudentSignUpResponseDto>> signUpStudent(@Valid @RequestBody SignUpStudentRequestDto request) {
        var res = service.signUpStudent(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Sign up a lessor")
    @PostMapping("/signUp/lessor")
    public ResponseEntity<ApiResponse<LessorSignUpResponseDto>> signUpLessor(@Valid @RequestBody SignUpLessorRequestDto request) {
        var res = service.signUpLessor(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Sign in a user")
    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse<SignInResponseDto>> signInUser(@Valid @RequestBody SignInUserRequestDto request) {
        var res = service.signIn(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Refresh the access token")
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<RefreshTokenResponseDto>> refreshToken(@Valid @RequestBody RefreshTokenRequestDto request) {
        var res = service.refreshToken(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}