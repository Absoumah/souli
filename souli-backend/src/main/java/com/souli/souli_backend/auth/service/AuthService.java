package com.souli.souli_backend.auth.service;

import com.souli.souli_backend.auth.dto.AuthResponseDto;
import com.souli.souli_backend.auth.dto.LoginRequestDto;
import com.souli.souli_backend.auth.dto.RegisterRequestDto;
import com.souli.souli_backend.common.exception.EmailAlreadyUsedException;
import com.souli.souli_backend.users.domain.User;
import com.souli.souli_backend.users.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            CustomUserDetailsService userDetailsService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public AuthResponseDto login(LoginRequestDto credentials) {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        credentials.getEmail(),
                        credentials.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new AuthResponseDto(
                jwtService.generateToken(userDetails),
                "Bearer",
                jwtService.getAccessTokenExpiration()
        );
    }

    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        User user = User.createPlayer(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFirstName(),
                request.getLastName(),
                request.getPhone()
        );
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        return new AuthResponseDto(
                jwtService.generateToken(userDetails),
                "Bearer",
                jwtService.getAccessTokenExpiration()
        );
    }
}
