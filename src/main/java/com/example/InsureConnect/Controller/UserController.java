package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Repository.UserRepository;
import com.example.InsureConnect.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //유저 정보 조회
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
        return "myPage";
    }

}
