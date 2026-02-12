package com.isaruh.sigh.security;

import com.isaruh.sigh.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${sigh.jwt.secret}")
    private String jwtSecret;

    @Value("${sigh.jwt.expiration}")
    private Long jwtExpiration;

    @Value("${sigh.jwt.issuer}")
    private String jwtIssuer;

    @Value("${sigh.jwt.refresh_expiration}")
    private Long jwtRefreshToken;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuer(jwtIssuer)
                .expiration(new Date(new Date().getTime() + jwtExpiration))
                .claim("role", "user")
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean verifyToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
