package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.ChatGptResponseDto;
import com.example.InsureConnect.Dto.QuestionRequestDto;
import com.example.InsureConnect.Service.ChatGptService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-gpt")
public class ChatGptController {

    private final ChatGptService chatGptService;

    public ChatGptController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/question")
    public ChatGptResponseDto sendQuestion(@RequestBody QuestionRequestDto requestDto) {
        //로그인 구현시 null 해제
        return chatGptService.askQuestion(requestDto, null);
    }
}