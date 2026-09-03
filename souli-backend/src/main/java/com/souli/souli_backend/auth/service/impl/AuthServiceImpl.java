package com.souli.souli_backend.auth.service.impl;

import com.souli.souli_backend.auth.dto.AuthResponseDto;
import com.souli.souli_backend.auth.dto.LoginRequestDto;
import com.souli.souli_backend.auth.dto.RegisterRequestDto;
import com.souli.souli_backend.auth.dto.RefreshTokenRequestDto;
import com.souli.souli_backend.auth.service.AuthService;
import com.souli.souli_backend.auth.service.CustomUserDetailsService;
import com.souli.souli_backend.auth.service.JwtService;
import com.souli.souli_backend.common.exception.SouliException;
import com.souli.souli_backend.users.domain.dto.UserDto;
import com.souli.souli_backend.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public AuthResponseDto login(LoginRequestDto credentials) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(
                            credentials.getEmail(),
                            credentials.getPassword()
                    )
            );
        } catch (BadCredentialsException exception) {
            throw new SouliException("Email ou mot de passe incorrect");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new AuthResponseDto(
                jwtService.generateToken(userDetails),
                jwtService.generateRefreshToken(userDetails),
                "Bearer",
                jwtService.getAccessTokenExpiration()
        );
    }

    @Override
    public AuthResponseDto refresh(RefreshTokenRequestDto request) {
        String refreshToken = request.refreshToken();
        String username;

        try {
            if (!jwtService.isRefreshToken(refreshToken)) {
                throw new SouliException("Refresh token invalide");
            }
            username = jwtService.extractUsername(refreshToken);
        } catch (RuntimeException exception) {
            if (exception instanceof SouliException souliException) {
                throw souliException;
            }
            throw new SouliException("Refresh token invalide ou expiré");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new AuthResponseDto(
                jwtService.generateToken(userDetails),
                jwtService.generateRefreshToken(userDetails),
                "Bearer",
                jwtService.getAccessTokenExpiration()
        );
    }

    @Override
    public UserDto register(RegisterRequestDto request) {
        return userService.register(request);
    }
}
