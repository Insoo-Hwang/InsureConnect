package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.Repository.PlannerRepository;
import com.example.InsureConnect.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final UserRepository userRepository;


    public void savePlanner(PlannerDto plannerDto, CustomOAuth2User user) {
        Optional<User> byKakaoId = userRepository.findByKakaoId(user.getId());
        Planner planner = Planner.builder()
                .id(plannerDto.getId())
                .user(byKakaoId.get())
                .company(plannerDto.getCompany())
                .profile(plannerDto.getProfile())
                .certificate(plannerDto.getCertificate())
                .status("enroll")
                .build();

        plannerRepository.save(planner);
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

}
