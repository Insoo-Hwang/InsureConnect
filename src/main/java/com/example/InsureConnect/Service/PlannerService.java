package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.Repository.PlannerRepository;
import com.example.InsureConnect.Repository.UserRepository;
import com.example.InsureConnect.Handler.FileUploadHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final UserRepository userRepository;
    private final FileUploadHandler fileUploadHandler;

    public void savePlanner(PlannerDto plannerDto, CustomOAuth2User user, MultipartFile profileImage, MultipartFile certificateImage) {
        try {
            String path = "classpath:/static/img/planner";
            String profileFileName = fileUploadHandler.uploadFile(profileImage, path);
            String certificateFileName = fileUploadHandler.uploadFile(certificateImage, path);

            Optional<User> byKakaoId = userRepository.findByKakaoId(user.getId());
            Planner planner = Planner.builder()
                    .id(plannerDto.getId())
                    .user(byKakaoId.get())
                    .company(plannerDto.getCompany())
                    .profile(profileFileName)
                    .certificate(certificateFileName)
                    .status("enroll")
                    .build();

            plannerRepository.save(planner);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error during file upload: " + e.getMessage());
        }
    }

    public PlannerDto findByUser_KakaoId(Long userId) {
        Planner byUserKakaoId = plannerRepository.findByUser_KakaoId(userId);
        return PlannerDto.builder()
                .id(byUserKakaoId.getId())
                .certificate(byUserKakaoId.getCertificate())
                .status(byUserKakaoId.getStatus())
                .company(byUserKakaoId.getCompany())
                .profile(byUserKakaoId.getProfile())
                .build();
    }

    public UserDto findUserById(Long planner_id) {
        User userById = plannerRepository.findUserByPlannerId(planner_id);
        return UserDto.toDto(userById);
    }
}
