package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.ChatDto;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Service.ChatGptService;
import com.example.InsureConnect.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatGptService chatGptService;
    private final UserService userService;

    @GetMapping("/chat")
    public String chat(@AuthenticationPrincipal CustomOAuth2User user, Model model) {
        UserDto userDto = userService.findByKakaoId(user.getId());
        List<ChatDto> chatDtos = chatGptService.chats(userDto.getId());
        model.addAttribute("chatDtos", chatDtos);
        model.addAttribute("userId", userDto.getId());
        if(user == null) model.addAttribute("login", false);
        else model.addAttribute("login", true);
        return "chat";
    }
}
