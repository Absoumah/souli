package com.souli.souli_backend.users.service.impl;

import com.souli.souli_backend.auth.dto.LoginRequestDto;
import com.souli.souli_backend.auth.dto.RegisterRequestDto;
import com.souli.souli_backend.users.domain.User;
import com.souli.souli_backend.users.domain.dto.UserDto;
import com.souli.souli_backend.users.mapper.UserMapper;
import com.souli.souli_backend.users.repository.UserRepository;
import com.souli.souli_backend.users.service.UserService;
import com.souli.souli_backend.users.service.UserValidatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserValidatorService userValidatorService;

    public UserDto register(RegisterRequestDto registerRequestDto) {
        userValidatorService.verifyCreation(registerRequestDto);
        User created = userRepository.save(userMapper.registerDtoToEntity(registerRequestDto));
        
        return userMapper.entityToUserDto(created);
    }
}
