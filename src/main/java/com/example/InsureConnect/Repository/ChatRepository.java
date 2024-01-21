package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.Chat;
import com.example.InsureConnect.Entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByChatRoom(ChatRoom chatRoom);
}
