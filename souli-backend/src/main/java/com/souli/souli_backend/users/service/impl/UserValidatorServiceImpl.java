package com.souli.souli_backend.users.service.impl;

import com.souli.souli_backend.auth.dto.RegisterRequestDto;
import com.souli.souli_backend.common.exception.SouliException;
import com.souli.souli_backend.users.domain.User;
import com.souli.souli_backend.users.repository.UserRepository;
import com.souli.souli_backend.users.service.UserValidatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserValidatorServiceImpl implements UserValidatorService {

    private final UserRepository userRepository;

    @Override
    public void verifyCreation(RegisterRequestDto registerRequestDto) {
        if (registerRequestDto == null) {
            throw new SouliException("L'utilisateur ne doit pas être null");
        }

        boolean existsByEmail = userRepository.existsByEmail(registerRequestDto.getEmail());
        
        if (existsByEmail) {
            throw new SouliException("Cet email est déjà utilisé");
        }
    }
}
