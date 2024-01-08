package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.ChatDto;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Service.ChatGptService;
import com.example.InsureConnect.Service.KakaoService;
import com.example.InsureConnect.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final UserService userService;

    @GetMapping("/test")
    public String test() {
        return "testqwer";
    }

    @GetMapping("/header")
    public String getTemplate(Model model) {
        return "header";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());

        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/chat")
    public String chat(@AuthenticationPrincipal CustomOAuth2User user, Model model) {
        UserDto userDto = userService.findByKakaoId(user.getId());
        List<ChatDto> chatDtos = chatGptService.chats(userDto.getId());
        model.addAttribute("chatDtos", chatDtos);
        model.addAttribute("userId", userDto.getId());
        return "chat";
    }

    @GetMapping("/promotion/new")
    public String promotion(){
        return "promotion";
    }
}
