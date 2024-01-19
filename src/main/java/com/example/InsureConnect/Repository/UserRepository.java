package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findById(UUID id);
    Optional<User> findByKakaoId(Long kakaoId);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE kakao_id IS NOT NULL ", nativeQuery = true)
    List<User> findByNotNull();


}
