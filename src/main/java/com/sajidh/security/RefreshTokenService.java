package com.sajidh.security;

import com.sajidh.config.JwtProperties;
import com.sajidh.model.AppUser;
import com.sajidh.model.RefreshToken;
import com.sajidh.repository.AppUserRepository;
import com.sajidh.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final AppUserRepository userRepository;
    private final JwtProperties jwtProperties;

    public RefreshTokenService(
            RefreshTokenRepository repository,
            AppUserRepository userRepository,
            JwtProperties jwtProperties
    ) {

        this.repository = repository;
        this.userRepository = userRepository;
        this.jwtProperties = jwtProperties;
    }

    public Optional<RefreshToken> findByToken(
            String token
    ) {

        return repository.findByToken(
                token
        );
    }


    public RefreshToken createRefreshToken(
            String username
    ) {
        AppUser user =
                userRepository.findByUsername(username)
                        .orElseThrow();

        repository.deleteByUser(user);

        RefreshToken refreshToken =
                new RefreshToken();

        refreshToken.setUser(user);

        refreshToken.setToken(
                UUID.randomUUID().toString()
        );

        refreshToken.setExpiryDate(
                Instant.now().plusMillis(
                        jwtProperties.getRefreshExpiration()
                )
        );

        return repository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(
            RefreshToken token
    ) {
        if (
                token.getExpiryDate()
                        .isBefore(
                                Instant.now()
                        )
        ) {

            repository.delete(token);

            throw new RuntimeException(
                    "Refresh token expired. Please login again."
            );
        }

        return token;
    }

    public void deleteRefreshToken(
            AppUser user
    ) {

        repository.deleteByUser(user);
    }
}
