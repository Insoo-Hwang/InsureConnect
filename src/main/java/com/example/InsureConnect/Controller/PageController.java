package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final KakaoService kakaoService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());

        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
