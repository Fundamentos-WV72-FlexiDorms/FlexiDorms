package com.techartistry.accountservice.user.presentation.controllers;

import com.techartistry.accountservice.user.application.dto.request.*;
import com.techartistry.accountservice.user.application.dto.response.LessorUpdateResponseDto;
import com.techartistry.accountservice.user.application.dto.response.ProfileResponseDto;
import com.techartistry.accountservice.user.application.dto.response.StudentUpdateResponseDto;
import com.techartistry.accountservice.user.application.services.IUserService;
import com.techartistry.accountservice.security.domain.model.UserPrincipal;
import com.techartistry.accountservice.shared.model.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Obtiene al usuario")
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponseDto>> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        var userData = userService.getUserProfile(userPrincipal);
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @Operation(summary = "Update a student")
    @PutMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<StudentUpdateResponseDto>> updateStudent(@Valid @RequestBody UpdateStudentRequestDto request, @PathVariable Long studentId){
        var res = userService.updateStudent(request, studentId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Update a lessor")
    @PutMapping("/lessor/{lessorId}")
    public ResponseEntity<ApiResponse<LessorUpdateResponseDto>> updateLessor(@Valid @RequestBody UpdateLessorRequestDto request, @PathVariable Long lessorId){
        var res = userService.updateLessor(request, lessorId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
