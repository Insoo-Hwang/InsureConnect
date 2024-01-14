package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.Repository.PlannerRepository;
import com.example.InsureConnect.Repository.UserRepository;
import com.example.InsureConnect.Handler.FileUploadHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final UserRepository userRepository;
    private final FileUploadHandler fileUploadHandler;
    private final ModelMapper modelMapper;

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

    public PlannerDto findById(Long planner_id) {
        Optional<Planner> planner = plannerRepository.findById(planner_id);
        return modelMapper.map(planner, PlannerDto.class);
    }
    public PlannerDto findByUser_KakaoId(Long userId) {
        Planner byUserKakaoId = plannerRepository.findByUser_KakaoId(userId);
        return modelMapper.map(byUserKakaoId, PlannerDto.class);
    }

    public UserDto findUserById(Long planner_id) {
        User userById = plannerRepository.findUserByPlannerId(planner_id);
        return modelMapper.map(userById, UserDto.class);
    }

    public PlannerDto findByUserId(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());
        Planner planner = plannerRepository.findByUser(user);
        if(planner == null) return null;
        else return modelMapper.map(planner, PlannerDto.class);
    }

    @Transactional
    public PlannerDto delete(Long id){
        Planner target = plannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());
        if(target.getStatus().equals("permit")){
            return null;
        }
        else {
            plannerRepository.delete(target);
            return modelMapper.map(target, PlannerDto.class);
        }
    }

    @Transactional
    public PlannerDto managePlanner(Long id, boolean permit){
        Planner target = plannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());
        target.changeStatus(permit);
        Planner updated = plannerRepository.save(target);
        return modelMapper.map(updated, PlannerDto.class);
    }

    public List<PlannerDto> findEnrollPlanner(){
        List<Planner> planners = plannerRepository.findByStatusEnroll();
        return planners.stream()
                .map(planner -> modelMapper.map(planner, PlannerDto.class))
                .collect(Collectors.toList());
    }

    public List<PlannerDto> findAll() {
        List<Planner> planners = plannerRepository.findAll();

        return planners.stream()
                .map(planner -> modelMapper.map(planner, PlannerDto.class))
                .collect(Collectors.toList());
    }

}