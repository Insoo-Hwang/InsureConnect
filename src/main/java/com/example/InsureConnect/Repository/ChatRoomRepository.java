package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.ChatRoom;
import com.example.InsureConnect.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByCode(UUID code);

    List<ChatRoom> findByUser(User user);
}
