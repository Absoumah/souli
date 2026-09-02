package com.souli.souli_backend.users.service;

import com.souli.souli_backend.auth.dto.RegisterRequestDto;
import com.souli.souli_backend.users.domain.dto.UserDto;

public interface UserService {
    UserDto register(RegisterRequestDto RegisterRequestDto);
}
