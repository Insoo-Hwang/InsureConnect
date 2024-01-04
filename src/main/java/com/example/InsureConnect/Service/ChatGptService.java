package com.example.InsureConnect.Service;

import com.example.InsureConnect.Config.ChatGptConfig;
import com.example.InsureConnect.Dto.ChatDto;
import com.example.InsureConnect.Dto.ChatGptRequestDto;
import com.example.InsureConnect.Dto.ChatGptResponseDto;
import com.example.InsureConnect.Dto.QuestionRequestDto;
import com.example.InsureConnect.Entity.Chat;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatGptService {
    private static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ChatRepository chatRepository;

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
    public ChatGptResponseDto askQuestion(QuestionRequestDto requestDto, User user) {
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
        saveChat(requestDto.getQuestion(), responseDto.getChoices().get(0).getText(), user);
        return responseDto;
    }

    //채팅 내용을 DB에 저장
    private void saveChat(String question, String answer, User user){
        ChatDto chatDto = new ChatDto();
        chatDto.setQuestion(question);
        chatDto.setAnswer(answer);
        chatDto.setTime(new Timestamp(System.currentTimeMillis()));
        chatRepository.save(Chat.toChat(chatDto, user));
    }

    //사용자의 모든 채팅 불러오기
    public List<ChatDto> chats(UUID userId){
        List<ChatDto> chats = chatRepository.findByUserId(userId)
                .stream()
                .map(chat -> ChatDto.toDto(chat))
                .collect(Collectors.toList());
        Collections.reverse(chats);
        return chats;
    }
}