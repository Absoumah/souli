package com.souli.souli_backend.auth.controller;

import com.souli.souli_backend.auth.dto.AuthResponseDto;
import com.souli.souli_backend.auth.dto.LoginRequestDto;
import com.souli.souli_backend.auth.dto.RegisterRequestDto;
import com.souli.souli_backend.auth.service.AuthService;
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

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto credentials) {
        return ResponseEntity.ok(authService.login(credentials));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.status(201).body(authService.register(request));
    }
}
