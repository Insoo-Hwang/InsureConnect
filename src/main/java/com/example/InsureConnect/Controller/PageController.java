package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.ChatDto;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Entity.Promotion;
import com.example.InsureConnect.Entity.PromotionImg;
import com.example.InsureConnect.Service.ChatGptService;
import com.example.InsureConnect.Service.PromotionService;
import com.example.InsureConnect.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final ChatGptService chatGptService;
    private final UserService userService;
    private final PromotionService promotionService;

    @GetMapping("/test")
    public String test() {
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

    @GetMapping("/promotion/{promotionId}")
    public String showPromotion(@PathVariable Long promotionId, Model model){
        Promotion promotion = promotionService.getPromotion(promotionId);
        List<PromotionImg> promotionImgs = promotionService.getPromotionImgs(promotionId);
        model.addAttribute("promotion", promotion);
        model.addAttribute("promotionImgs", promotionImgs);
        return "promotion_detail";
    }
}
