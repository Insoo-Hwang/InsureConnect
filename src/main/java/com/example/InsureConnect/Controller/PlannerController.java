package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Service.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
@RequiredArgsConstructor
public class PlannerController {
    private final ResourceLoader resourceLoader;
    private final PlannerService plannerService;

    //Planner 등록

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
        try {
            // profileImage와 certificateImage가 null이 아니라면 해당 이미지를 업로드
            if (profileImage != null) {
                String profileFileName = uploadFile(profileImage);
                plannerDto.setProfile(profileFileName);
                System.out.println("profileFileName = " + profileFileName);
            }

            if (certificateImage != null) {
                String certificateFileName = uploadFile(certificateImage);
                plannerDto.setCertificate(certificateFileName);
                System.out.println("certificateFileName = " + certificateFileName);
            }
            plannerDto.setCompany(company);

            // 여기에서 필요한 비즈니스 로직 수행
            plannerService.savePlanner(plannerDto, user);


        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage());
        }

        return "home";
    }

    // 파일 업로드를 처리하는 메서드
    private String uploadFile(MultipartFile file) throws IOException {
        // 저장할 파일 경로
        Path storagePath = resourceLoader.getResource("classpath:/static/img/planner/").getFile().toPath().resolve(file.getOriginalFilename());

        // 파일을 저장
        Files.copy(file.getInputStream(), storagePath, StandardCopyOption.REPLACE_EXISTING);

        // 파일명 반환
        return storagePath.toString();

    }

}
