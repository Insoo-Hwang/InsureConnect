package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(UUID userId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}