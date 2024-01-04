package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
