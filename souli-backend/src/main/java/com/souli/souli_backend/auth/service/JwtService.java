package com.souli.souli_backend.auth.service;

import com.souli.souli_backend.auth.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final SecretKey signingKey;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, "access", jwtProperties.getAccessTokenExpiration());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, "refresh", jwtProperties.getRefreshTokenExpiration());
    }

    public boolean isRefreshToken(String token) {
        return "refresh".equals(extractClaims(token).get("token_type", String.class));
    }

    private String generateToken(UserDetails userDetails, String tokenType, long expirationInSeconds) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(expirationInSeconds);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("token_type", tokenType)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(signingKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return !isRefreshToken(token)
                && extractUsername(token).equals(userDetails.getUsername())
                && userDetails.isEnabled();
    }

    public long getAccessTokenExpiration() {
        return jwtProperties.getAccessTokenExpiration();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
