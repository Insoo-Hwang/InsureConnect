package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.ChatDto;
import com.example.InsureConnect.Dto.ChatRoomDto;
import com.example.InsureConnect.Dto.ConsultRequestDto;
import com.example.InsureConnect.Service.ChatGptService;
import com.example.InsureConnect.Service.ChatRoomService;
import com.example.InsureConnect.Service.ConsultRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ConsultRequestController {
    private final ChatRoomService chatRoomService;
    private final ChatGptService chatGptService;
    private final ConsultRequestService consultRequestService;

    @GetMapping("/chatRoom/{code}/{plannerId}/{requestId}")
    public String chat(@PathVariable("code") UUID code,
                       @PathVariable("plannerId") Long plannerId,
                       @PathVariable("requestId") Long requestId,
                       Model model) {
        ConsultRequestDto byPlannerId = consultRequestService.findByPlannerId(plannerId, code, requestId);
        ChatRoomDto chatRoomDto = chatRoomService.findByCode(byPlannerId.getChatRoomId());
        List<ChatDto> chatDtos = chatGptService.chats(chatRoomDto.getId());

        model.addAttribute("chatDtos", chatDtos);
        model.addAttribute("chatRoomId", chatRoomDto.getId());
        return "chat";
    }
}
