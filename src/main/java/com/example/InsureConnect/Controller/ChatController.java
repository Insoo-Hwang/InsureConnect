package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.ChatDto;
import com.example.InsureConnect.Dto.ChatRoomDto;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Config.OAuth.CustomOAuth2User;
import com.example.InsureConnect.Service.ChatGptService;
import com.example.InsureConnect.Service.ChatRoomService;
import com.example.InsureConnect.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatGptService chatGptService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @GetMapping("/chat")
    public String chatroom(@AuthenticationPrincipal CustomOAuth2User user, Model model){
        UserDto dto = userService.findByKakaoId(user.getId());
        List<ChatRoomDto> chatRoomDtos = chatRoomService.showAll(dto.getId());
        model.addAttribute("chatRoomDtos", chatRoomDtos);
        model.addAttribute("userId", dto.getId());
        return "chatRoom";
    }

    @GetMapping("/chat/{code}")
    public String chat(@PathVariable UUID code, Model model) {
        ChatRoomDto chatRoomDto = chatRoomService.findByCode(code);
        List<ChatDto> chatDtos = chatGptService.chats(chatRoomDto.getId());
        model.addAttribute("chatDtos", chatDtos);
        model.addAttribute("chatRoomId", chatRoomDto.getId());
        return "chat";
    }
}
