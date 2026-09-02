package com.souli.souli_backend.users.domain.dto;

import com.souli.souli_backend.users.domain.enums.Role;
import lombok.Data;

import java.time.Instant;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private Instant createdAt;
}
