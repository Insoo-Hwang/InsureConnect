package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.ChatRoomDto;
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

    @GetMapping("/api/chatroom/{userId}")
    public ResponseEntity<List<ChatRoomDto>> showAll(@PathVariable UUID userId){
        List<ChatRoomDto> chatRoomDtos = chatRoomService.showAll(userId);
        return ResponseEntity.status(HttpStatus.OK).body(chatRoomDtos);
    }

    @PostMapping("/api/chatroom/new")
    public ResponseEntity<ChatRoomDto> create(@RequestBody ChatRoomDto dto){
        ChatRoomDto created = chatRoomService.create(dto);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @DeleteMapping("/api/chatroom/{id}")
    public void delete(@PathVariable Long id){
        chatRoomService.delete(id);
    }
}
