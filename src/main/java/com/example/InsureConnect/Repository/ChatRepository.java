package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = "SELECT * FROM chat WHERE user_id = :userId", nativeQuery = true)
    List<Chat> findByUserId(UUID userId);
}
