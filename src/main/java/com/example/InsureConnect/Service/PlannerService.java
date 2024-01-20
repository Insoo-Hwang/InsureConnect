package com.example.InsureConnect.Service;

import com.example.InsureConnect.Config.PlannerSpecification;
import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Config.OAuth.CustomOAuth2User;
import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.handler.FileUploadHandler;
import com.example.InsureConnect.Repository.PlannerRepository;
import com.example.InsureConnect.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
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

    public PlannerDto findByUserId(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        Planner planner = plannerRepository.findByUser(user);
        if (planner == null) return null;
        else return modelMapper.map(planner, PlannerDto.class);
    }

    @Transactional
    public PlannerDto delete(Long id) {
        Planner target = plannerRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        if (target.getStatus().equals("permit")) {
            return null;
        } else {
            plannerRepository.delete(target);
            return modelMapper.map(target, PlannerDto.class);
        }
    }

    @Transactional
    public PlannerDto managePlanner(Long id, boolean permit) {
        Planner target = plannerRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        target.changeStatus(permit);
        Planner updated = plannerRepository.save(target);
        return modelMapper.map(updated, PlannerDto.class);
    }


    @Transactional
    public PlannerDto deletePlanner(Long id){
        Planner target = plannerRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        target.deleteStatus();
        Planner deleted = plannerRepository.save(target);
        return modelMapper.map(deleted, PlannerDto.class);
    }

    public List<PlannerDto> findEnrollPlanner(){
        List<Planner> planners = plannerRepository.findByStatusEnroll();
        return planners.stream()
                .map(planner -> modelMapper.map(planner, PlannerDto.class))
                .collect(Collectors.toList());
    }

    public List<PlannerDto> findAllAllPermitPlanner() {
        List<Planner> planners = plannerRepository.findAllPermitPlanner();

        return planners.stream()
                .map(planner -> modelMapper.map(planner, PlannerDto.class))
                .collect(Collectors.toList());
    }


    public Slice<PlannerDto> findAllPermitPlanner(Pageable pageable, String sort, String search, String criteria) {
        Specification<Planner> spec = PlannerSpecification.buildSpecification(search, criteria, sort);

        return plannerRepository.findAll(spec, pageable)
                .map(planner -> modelMapper.map(planner, PlannerDto.class));
    }


    public List<PlannerDto> recommendPlanner(){
        List<Planner> planners = plannerRepository.findAllPermitPlanner();
        List<Recommend> recommends = new ArrayList<>();
        for(Planner planner : planners){
            recommends.add(new Recommend(planner.getReview().size(), planner.getRecommendRating(), modelMapper.map(planner, PlannerDto.class)));
        }
        Collections.sort(recommends);
        List<PlannerDto> plannerDtos = new ArrayList<>();
        for(Recommend recommend : recommends){
            plannerDtos.add(recommend.getPlannerDto());
        }
        return plannerDtos;
    }

    @Getter
    @AllArgsConstructor
    static class Recommend implements Comparable<Recommend>{
        int cnt;
        int score;
        PlannerDto plannerDto;

        @Override
        public int compareTo(Recommend o) {
            if(this.score == o.score) return o.cnt-this.cnt;
            else return o.score-this.score;
        }
    }
}
