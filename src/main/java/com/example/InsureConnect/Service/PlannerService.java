package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.Handler.FileUploadHandler;
import com.example.InsureConnect.Repository.ConnectCategoryRepository;
import com.example.InsureConnect.Repository.PlannerRepository;
import com.example.InsureConnect.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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

    public PlannerDto savePlanner(PlannerDto plannerDto, CustomOAuth2User user, MultipartFile profileImage, MultipartFile certificateImage) {
        try {
            String path = "classpath:/static/img/planner";
            String profileFileName = fileUploadHandler.uploadFile(profileImage, path);
            String certificateFileName = fileUploadHandler.uploadFile(certificateImage, path);

            User byKakaoId = userRepository.findByKakaoId(user.getId()).orElseThrow(IllegalArgumentException::new);

            Planner planner = Planner.builder()
                    .id(plannerDto.getId())
                    .user(byKakaoId)
                    .company(plannerDto.getCompany())
                    .profile(profileFileName)
                    .certificate(certificateFileName)
                    .status("enroll")
                    .build();

            Planner savedPlanner = plannerRepository.save(planner);
            return modelMapper.map(savedPlanner, PlannerDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error during file upload: " + e.getMessage());
        }
    }

    public PlannerDto findById(Long plannerId) {
        Optional<Planner> planner = plannerRepository.findById(plannerId);
        return modelMapper.map(planner, PlannerDto.class);
    }
    public PlannerDto findByUser_KakaoId(Long userId) {
        Planner byUserKakaoId = plannerRepository.findByUser_KakaoId(userId);
        return modelMapper.map(byUserKakaoId, PlannerDto.class);
    }

    public UserDto findUserById(Long plannerId) {
        User userById = plannerRepository.findUserByPlannerId(plannerId);
        return modelMapper.map(userById, UserDto.class);
    }

    public PlannerDto findByUserId(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        Planner planner = plannerRepository.findByUser(user);
        if(planner == null) return null;
        else return modelMapper.map(planner, PlannerDto.class);
    }

    @Transactional
    public PlannerDto delete(Long id){
        Planner target = plannerRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
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
                .orElseThrow(IllegalArgumentException::new);
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