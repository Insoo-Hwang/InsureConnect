package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Entity.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PlannerController {

    @GetMapping("/planner/new")
    public String registerPlanner(@AuthenticationPrincipal CustomOAuth2User user, Model model) {
        model.addAttribute("user_id", user.getId());
        return "signup_planner";
    }
}
