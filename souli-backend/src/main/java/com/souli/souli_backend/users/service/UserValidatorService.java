package com.souli.souli_backend.users.service;

import com.souli.souli_backend.auth.dto.LoginRequestDto;
import com.souli.souli_backend.auth.dto.RegisterRequestDto;

public interface UserValidatorService {
    void verifyCreation(RegisterRequestDto registerRequestDto);
}
