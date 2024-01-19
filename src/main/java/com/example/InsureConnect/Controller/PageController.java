package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.*;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Entity.Promotion;
import com.example.InsureConnect.Entity.PromotionImg;
import com.example.InsureConnect.Service.*;
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
    private final PlannerService plannerService;
    private final CategoryService categoryService;

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal CustomOAuth2User user,Model model) {
        model.addAttribute("user", user);
        return "testqwer";
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal CustomOAuth2User user, Model model) {
        if(user == null){
            model.addAttribute("user", "로그인을 해주세요!");
            model.addAttribute("url", "/oauth2/authorization/kakao");
        }
        else {
            UserDto dto = userService.findByKakaoId(user.getId());
            model.addAttribute("user", dto.getNickname()+"님 안녕하세요!");
            model.addAttribute("url", "/logout");
        }
        return "home";
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
        PlannerDto plannerDto = plannerService.findByUserId(dto.getId());
        String plannerStatus = "";
        if(plannerDto == null) plannerStatus = "설계사를 신청해보세요!";
        else if(plannerDto.getStatus().equals("enroll")) plannerStatus = "신청중";
        else if(plannerDto.getStatus().equals("permit")) plannerStatus = "설계사";
        else if(plannerDto.getStatus().equals("temp")) plannerStatus = "반려";
        model.addAttribute("plannerStatus", plannerStatus);
        if(plannerDto != null) model.addAttribute("plannerId", plannerDto.getId());
        return "myPage";
    }

    @GetMapping("/management/planner")
    public String managePlanner(Model model){
        List<PlannerDto> plannerDtos = plannerService.findEnrollPlanner();
        model.addAttribute("plannerDtos", plannerDtos);
        return "management_planner";
    }

    @GetMapping("/management/user")
    public String manageUser(Model model){
        List<UserDto> userDtos = userService.showAll();
        for (UserDto userDto : userDtos) {
            System.out.println("userDto = " + userDto);
        }
        model.addAttribute("userDtos", userDtos);
        return "management_user";
    }

    @GetMapping("/management/review")
    public String manageReview(Model model){
        List<ReviewDto> reviewDtos = reviewService.findAll();
        model.addAttribute("reviewDtos", reviewDtos);
        return "management_review";
    }

    @GetMapping("/management/category")
    public String manageCategory(Model model){
        List<CategoryDto> categoryDtos = categoryService.showAll();
        model.addAttribute("categoryDtos", categoryDtos);
        return "management_category";
    }

    @GetMapping("/management")
    public String manage(Model model){
        return "management";
    }
}
