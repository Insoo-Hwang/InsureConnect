package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.ChatRoomDto;
import com.example.InsureConnect.Entity.ChatRoom;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.Repository.ChatRoomRepository;
import com.example.InsureConnect.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatGptService chatGptService;
    private final ModelMapper modelMapper;

    @Transactional
    public ChatRoomDto create(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());
        ChatRoom chatRoom = ChatRoom.builder()
                .user(user)
                .build();
        ChatRoom created = chatRoomRepository.save(chatRoom);
        return modelMapper.map(created, ChatRoomDto.class);
    }

    public List<ChatRoomDto> showAll(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());
        List<ChatRoom> chatRooms = chatRoomRepository.findByUser(user);
        return chatRooms.stream()
                .map(chatRoom -> modelMapper.map(chatRoom, ChatRoomDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long chatRoomId){
        chatGptService.deleteAll(chatRoomId);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new IllegalArgumentException());
        chatRoomRepository.delete(chatRoom);
    }

    public ChatRoomDto findByCode(UUID code) {
        ChatRoom chatRoom = chatRoomRepository.findByCode(code).orElseThrow(() -> new IllegalArgumentException());
        return modelMapper.map(chatRoom, ChatRoomDto.class);
    }
}
