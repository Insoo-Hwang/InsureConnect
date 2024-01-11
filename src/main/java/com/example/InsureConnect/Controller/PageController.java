package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.ChatDto;
import com.example.InsureConnect.Dto.ReviewDto;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Entity.Promotion;
import com.example.InsureConnect.Entity.PromotionImg;
import com.example.InsureConnect.Service.ChatGptService;
import com.example.InsureConnect.Service.PromotionService;
import com.example.InsureConnect.Service.ReviewService;
import com.example.InsureConnect.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;
    private final ReviewService reviewService;

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
    public String login() {
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

    @GetMapping("/mypage")
    public String showAttribute(@AuthenticationPrincipal CustomOAuth2User user, Model model){
        UserDto dto = userService.findByKakaoId(user.getId());
        String nickname = dto.getNickname();
        if(nickname == null) nickname = "닉네임을 입력해주세요!";
        int age = dto.getAge();
        String gender = dto.getGender();
        if(gender == null) gender = "성별을 입력해주세요!";
        model.addAttribute("userId", dto.getId());
        model.addAttribute("nickname", nickname);
        model.addAttribute("age", age);
        model.addAttribute("gender", gender);
        List<ReviewDto> reviewDtos = reviewService.findByUserId(dto.getId());
        model.addAttribute("reviewDtos", reviewDtos);
        return "myPage";
    }
}
