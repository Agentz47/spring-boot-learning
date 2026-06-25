package com.sajidh.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "mySuperSecretKeyForJwtGeneration123456789";

    public String generateToken(
            String username
    ) {

        SecretKey key =
                Keys.hmacShaKeyFor(
                        SECRET_KEY.getBytes()
                );

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                + 86400000
                        )
                )
                .signWith(key)
                .compact();
    }

    public String extractUsername(
            String token
    ) {

        SecretKey key =
                Keys.hmacShaKeyFor(
                        SECRET_KEY.getBytes()
                );

        Claims claims =
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

        return claims.getSubject();
    }

    public boolean isTokenValid(
            String token
    ) {

        try {

            extractUsername(token);

            return true;

        } catch (Exception e) {

            return false;
        }

    }
}
