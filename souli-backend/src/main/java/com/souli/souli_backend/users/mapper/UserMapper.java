package com.souli.souli_backend.users.mapper;

import com.souli.souli_backend.auth.dto.RegisterRequestDto;
import com.souli.souli_backend.users.domain.User;
import com.souli.souli_backend.users.domain.dto.UserDto;
import com.souli.souli_backend.users.domain.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User registerDtoToEntity(RegisterRequestDto dto) {
        User user = new User();

        if (dto != null) {
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setEmail(dto.getEmail());
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
            user.setPhone(dto.getPhone());
            user.setRole(Role.PLAYER);
        }

        return user;
    }

    public UserDto entityToUserDto(User created) {
        UserDto userDto = new UserDto();
        
        if (created != null) {
            userDto.setId(created.getId());    
            userDto.setFirstName(created.getFirstName());    
            userDto.setLastName(created.getLastName());
            userDto.setEmail(created.getEmail());    
            userDto.setPhone(created.getPhone()); 
            userDto.setRole(created.getRole());
            userDto.setCreatedAt(created.getCreatedAt());
        }
        
        return userDto;
    }
}
