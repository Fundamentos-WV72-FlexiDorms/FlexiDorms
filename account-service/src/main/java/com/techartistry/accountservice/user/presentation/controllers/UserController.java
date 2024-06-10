package com.techartistry.accountservice.user.presentation.controllers;

import com.techartistry.accountservice.user.application.dto.request.UpdateUserRequestDto;
import com.techartistry.accountservice.user.application.dto.response.UserProfileResponseDto;
import com.techartistry.accountservice.user.application.services.IUserService;
import com.techartistry.accountservice.shared.model.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get JWT token")
    @GetMapping("/jwt")
    public ResponseEntity<?> getProfile2(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(jwt.getTokenValue());
    }

    @Operation(summary = "Get a user by id")
    @GetMapping("/id/{userId}")
    public ResponseEntity<ApiResponse<UserProfileResponseDto>> getUserById(@PathVariable String userId) {
        var userData = userService.getUserById(userId);
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @Operation(summary = "Get all users")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserProfileResponseDto>>> getAllUsers() {
        var users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Get a user by username")
    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<List<UserProfileResponseDto>>> getUserByUsername(@PathVariable String username) {
        var userData = userService.searchUserByUsername(username);
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @Operation(summary = "Get a user by email")
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<List<UserProfileResponseDto>>> getUserByEmail(@PathVariable String email) {
        var userData = userService.searchUserByEmail(email);
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @Operation(summary = "Search a user by custom attribute. Example: /search?query=attributeName:attributeValue")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserProfileResponseDto>>> getUserByCustomAttribute(@RequestParam String query) {
        var userData = userService.searchByCustomAttribute(query);
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @Operation(summary = "Get a user by role")
    @GetMapping("/role/{roleName}")
    public ResponseEntity<ApiResponse<List<UserProfileResponseDto>>> getUserByRole(@PathVariable String roleName) {
        var userData = userService.searchByRole(roleName);
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @Operation(summary = "Update a user")
    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<Object>> updateStudent(@Valid @RequestBody UpdateUserRequestDto request, @PathVariable String userId){
        var res = userService.updateUser(request, userId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable String userId) {
        var res = userService.deleteUserByUserId(userId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
