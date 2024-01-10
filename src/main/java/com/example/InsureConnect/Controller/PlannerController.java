package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Service.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class PlannerController {
    private final PlannerService plannerService;

    @GetMapping("/registerPlanner")
    public String registerPlanner(@AuthenticationPrincipal CustomOAuth2User user, Model model) {
        model.addAttribute("user_id", user.getId());
        return "signup_planner";
    }

    @PostMapping("/registerPlanner")
    public String uploadPlanner(@RequestParam(value = "company") String company,
                                @RequestPart(value = "profileImage") MultipartFile profileImage,
                                @RequestPart(value = "certificateImage") MultipartFile certificateImage,
                                @AuthenticationPrincipal CustomOAuth2User user) {

        PlannerDto plannerDto = new PlannerDto();
        plannerDto.setCompany(company);

        plannerService.savePlanner(plannerDto, user, profileImage, certificateImage);

        return "home";

    }

}
