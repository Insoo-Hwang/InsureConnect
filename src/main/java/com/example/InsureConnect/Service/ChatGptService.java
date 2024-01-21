package com.example.InsureConnect.Service;

import com.example.InsureConnect.Config.ChatGptConfig;
import com.example.InsureConnect.Dto.*;
import com.example.InsureConnect.Entity.Chat;
import com.example.InsureConnect.Entity.ChatRoom;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.Repository.ChatRepository;
import com.example.InsureConnect.Repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatGptService {
    private static RestTemplate restTemplate = new RestTemplate();

    private final ChatRepository chatRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    public HttpEntity<ChatGptRequestDto> buildHttpEntity(ChatGptRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
        return new HttpEntity<>(requestDto, headers);
    }

    //chat gpt api를 통해 받은 답변
    public ChatGptResponseDto getResponse(HttpEntity<ChatGptRequestDto> chatGptRequestDtoHttpEntity) {
        ResponseEntity<ChatGptResponseDto> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                chatGptRequestDtoHttpEntity,
                ChatGptResponseDto.class);

        return responseEntity.getBody();
    }

    //chat gpt api를 통해 질문 전송
    public ChatGptResponseDto askQuestion(QuestionRequestDto requestDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(requestDto.getId()).orElseThrow(() -> new IllegalArgumentException());
        ChatGptResponseDto responseDto = this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequestDto(
                                ChatGptConfig.MODEL,
                                requestDto.getQuestion(),
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.TOP_P
                        )
                )
        );
        saveChat(requestDto.getQuestion(), responseDto.getChoices().get(0).getText(), chatRoom);
        return responseDto;
    }

    //채팅 내용을 DB에 저장
    private void saveChat(String question, String answer, ChatRoom chatRoom){
        ChatDto chatDto = new ChatDto();
        chatDto.setQuestion(question);
        chatDto.setAnswer(answer);
        chatDto.setTime(new Timestamp(System.currentTimeMillis()));
        chatDto.setChatRoomDto(modelMapper.map(chatRoom, ChatRoomDto.class));
        chatRepository.save(modelMapper.map(chatDto, Chat.class));
    }

    //채팅방의 모든 채팅 불러오기
    public List<ChatDto> chats(Long chatRoomId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new IllegalArgumentException());

        return chatRepository.findByChatRoom(chatRoom)
                .stream()
                .map(chat -> modelMapper.map(chat, ChatDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAll(Long chatRoomId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new IllegalArgumentException());
        List<Chat> chats = chatRepository.findByChatRoom(chatRoom);
        for(Chat chat : chats){
            chatRepository.delete(chat);
        }
    }
}