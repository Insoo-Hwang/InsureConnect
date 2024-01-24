package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.ConsultRequestDto;
import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Config.OAuth.CustomOAuth2User;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Service.ConnectCategoryService;
import com.example.InsureConnect.Service.ConsultRequestService;
import com.example.InsureConnect.Service.PlannerService;
import com.example.InsureConnect.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PlannerController {
    private final PlannerService plannerService;
    private final ConsultRequestService consultRequestService;
    private final ConnectCategoryService connectCategoryService;

    @GetMapping("/planner")
    public String showPlanner(@AuthenticationPrincipal CustomOAuth2User user, Model model) {
        PlannerDto planner = plannerService.findByUser_KakaoId(user.getId());
        List<ConsultRequestDto> requests = consultRequestService.findAllRequest(planner.getId());
        model.addAttribute("planner", planner);
        model.addAttribute("requests", requests);
        return "plannerPage";
    }
    @GetMapping("/planner/new")
    public String registerPlanner(@AuthenticationPrincipal CustomOAuth2User user, Model model) {
        model.addAttribute("user_id", user.getId());
        return "signup_planner";
    }

    //설계사 등록
    @PostMapping("/planner/save")
    public String uploadPlanner(@RequestParam(value = "company") String company,
                                @RequestParam(value = "category_id") List<Long> categoryIdList,
                                @RequestParam(value = "kakaoLink") String kakaoLink,
                                @RequestPart(value = "profileImage") MultipartFile profileImage,
                                @RequestPart(value = "certificateImage") MultipartFile certificateImage,
                                @AuthenticationPrincipal CustomOAuth2User user) {

        PlannerDto plannerDto = new PlannerDto();
        plannerDto.setCompany(company);
        plannerDto.setKakaoLink(kakaoLink);

        PlannerDto planner = plannerService.savePlanner(plannerDto, user, profileImage, certificateImage);
        connectCategoryService.saveCategory(categoryIdList, planner.getId());

        return "home";
    }
}
