package com.isaias.prospery_bussines_api.auth.jwt;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.user.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(UserEntity user) {
        return buildToken(user, jwtExpiration);
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUuid(String token) {
        Claims claims = Jwts.parser()
             .verifyWith(getSignInKey()) 
             .build()
             .parseSignedClaims(token)
             .getPayload();

        return claims.get("uuid", String.class);
    }

    private String buildToken(UserEntity user, long expiration) {
        return Jwts.builder()
                .id(user.getId().toString())
                .claims(Map.of("uuid", user.getId().toString()))
                .subject(user.getId().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();

    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
