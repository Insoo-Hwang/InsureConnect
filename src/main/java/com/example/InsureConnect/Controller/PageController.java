package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.ChatDto;
import com.example.InsureConnect.Service.ChatGptService;
import com.example.InsureConnect.Service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final KakaoService kakaoService;
    private final ChatGptService chatGptService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());

        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/chat/{userId}")
    public String chat(Model model, @PathVariable UUID userId){
        List<ChatDto> chatDtos = chatGptService.chats(userId);
        model.addAttribute("chatDtos", chatDtos);
        return "chat";
    }

    //TEST
    @GetMapping("/chat")
    public String chatTest(Model model){
        List<ChatDto> chatDtos = chatGptService.chatTest();
        model.addAttribute("chatDtos", chatDtos);
        return "chat";
    }
}
