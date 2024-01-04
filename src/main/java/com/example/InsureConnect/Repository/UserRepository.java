package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByKakaoId(Long kakaoId);
}
