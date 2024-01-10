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

@Controller
@RequiredArgsConstructor
public class PageController {

    private final KakaoService kakaoService;
    private final ChatGptService chatGptService;
    private final UserService userService;

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal CustomOAuth2User user,Model model) {
        model.addAttribute("user", user);
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

    @GetMapping("/logintest")
    public String home(@AuthenticationPrincipal CustomOAuth2User user, Model model) {
        model.addAttribute("we", user.getId());
        return "logintest";
    }

}
