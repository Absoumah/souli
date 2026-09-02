package com.souli.souli_backend.auth.controller;

import com.souli.souli_backend.auth.dto.AuthResponseDto;
import com.souli.souli_backend.auth.dto.LoginRequestDto;
import com.souli.souli_backend.auth.dto.RegisterRequestDto;
import com.souli.souli_backend.auth.service.AuthService;
import com.souli.souli_backend.common.dto.ApiResponseDto;
import com.souli.souli_backend.users.domain.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<UserDto>> register(
            @RequestBody RegisterRequestDto request
    ) {
        UserDto user = authService.register(request);
        ApiResponseDto<UserDto> response = new ApiResponseDto<>(
                HttpStatus.CREATED.value(),
                "Utilisateur créé avec succès",
                user
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<AuthResponseDto>> login(
            @RequestBody LoginRequestDto request
    ) {
        AuthResponseDto authentication = authService.login(request);
        ApiResponseDto<AuthResponseDto> response = new ApiResponseDto<>(
                HttpStatus.OK.value(),
                "Connexion réussie",
                authentication
        );

        return ResponseEntity.ok(response);
    }
}
