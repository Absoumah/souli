package com.souli.souli_backend.auth.service;

import com.souli.souli_backend.auth.dto.AuthResponseDto;
import com.souli.souli_backend.auth.dto.LoginRequestDto;
import com.souli.souli_backend.auth.dto.RegisterRequestDto;
import com.souli.souli_backend.auth.dto.RefreshTokenRequestDto;
import com.souli.souli_backend.users.domain.dto.UserDto;

public interface AuthService {

    AuthResponseDto login(LoginRequestDto credentials);

    AuthResponseDto refresh(RefreshTokenRequestDto request);

    UserDto register(RegisterRequestDto request);
}
