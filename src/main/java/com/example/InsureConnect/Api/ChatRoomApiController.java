package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.ChatDto;
import com.example.InsureConnect.Dto.ChatRoomDto;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Service.ChatGptService;
import com.example.InsureConnect.Service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChatRoomApiController {

    private final ChatRoomService chatRoomService;
    private final ChatGptService chatGptService;

    @GetMapping("/api/chatroom/{chatRoomId}")
    public ResponseEntity<List<ChatDto>> check(@PathVariable Long chatRoomId){
        List<ChatDto> chatDtos = chatGptService.chats(chatRoomId);
        if(chatDtos.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        else return ResponseEntity.status(HttpStatus.OK).body(chatDtos);
    }

    @PostMapping("/api/chatroom/{userId}")
    public ResponseEntity<ChatRoomDto> create(@PathVariable UUID userId){
        ChatRoomDto created = chatRoomService.create(userId);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @DeleteMapping("/api/chatroom/{id}")
    public void delete(@PathVariable Long id){
        chatRoomService.delete(id);
    }
}
