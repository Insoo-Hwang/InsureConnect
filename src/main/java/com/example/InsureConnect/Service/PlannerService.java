package com.example.InsureConnect.Service;

import com.example.InsureConnect.Config.OAuth.CustomOAuth2User;
import com.example.InsureConnect.Config.Paging.PlannerSpecification;
import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Dto.RecommendDto;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.RecommendPlanner;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.Handler.FileHandler;
import com.example.InsureConnect.Repository.PlannerRepository;
import com.example.InsureConnect.Repository.RecommendPlannerRepository;
import com.example.InsureConnect.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final UserRepository userRepository;

    private final FileHandler fileHandler;
    private final RecommendPlannerRepository recommendPlannerRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public PlannerDto savePlanner(String company, String kakaoLink, CustomOAuth2User user, MultipartFile profileImage, MultipartFile certificateImage) throws IOException {
        Map<String, String> images = saveImages(profileImage, certificateImage);
        PlannerDto plannerDto = new PlannerDto();
        plannerDto.setCompany(company);
        plannerDto.setKakaoLink(kakaoLink);
        plannerDto.setCertificate(images.get("certificatePath"));
        plannerDto.setProfile(images.get("profilePath"));

        User byKakaoId = getUserByKakaoId(user);

        return createPlanner(byKakaoId, plannerDto);
    }

    public PlannerDto findById(Long plannerId) {
        Planner planner = getPlannerById(plannerId);
        return modelMapper.map(planner, PlannerDto.class);
    }

    public PlannerDto findByUser_KakaoId(CustomOAuth2User user) {
        Planner plannerByKakaoId = getPlannerByKakaoId(user);
        return modelMapper.map(plannerByKakaoId, PlannerDto.class);
    }

    public UserDto findUserById(Long plannerId) {
        User userByPlannerId = getUserByPlannerId(plannerId);
        return modelMapper.map(userByPlannerId, UserDto.class);
    }

    public PlannerDto findByUserId(UUID userId) {
        User userById = getUserById(userId);
        Planner planner = getPlannerByUser(userById);
        if (planner == null) return null;
        else return modelMapper.map(planner, PlannerDto.class);
    }

    @Transactional
    public PlannerDto managePlanner(Long id, boolean permit) {
        Planner target = getPlannerById(id);
        target.changeStatus(permit);
        Planner updated = plannerRepository.save(target);
        return modelMapper.map(updated, PlannerDto.class);
    }

    @Transactional
    public PlannerDto delete(Long id) {
        Planner target = getPlannerById(id);
        if (target.getStatus().equals("permit")) {
            return null;
        } else {
            plannerRepository.delete(target);
            return modelMapper.map(target, PlannerDto.class);
        }
    }

    @Transactional
    public PlannerDto deletePlanner(Long id) {
        Planner target = getPlannerById(id);
        target.deleteStatus();
        Planner deleted = plannerRepository.save(target);
        return modelMapper.map(deleted, PlannerDto.class);
    }

    public List<PlannerDto> findEnrollPlanner() {
        List<Planner> planners = plannerRepository.findByStatusEnroll();
        return planners.stream()
                .map(planner -> modelMapper.map(planner, PlannerDto.class))
                .collect(Collectors.toList());
    }

    public List<PlannerDto> findPermitPlanner() {
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


    @Transactional
    public RecommendDto recommendPlanner() {
        List<PlannerDto> plannerDtos = new ArrayList<>();
        RecommendPlanner recommendPlanner = getLatestRecommendPlanner();

        if (isValidRecommendPlanner(recommendPlanner)) {
            plannerDtos.addAll(getPlannerDtosFromList(recommendPlanner.getList()));
        } else {
            plannerDtos.addAll(getPlannerDtosFromTopRecommendations());
            recommendPlanner = createAndSaveRecommendPlanner(plannerDtos);
        }

        return RecommendDto.builder()
                .list(plannerDtos)
                .time(recommendPlanner.getTime())
                .build();
    }

    private User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    }

    private Planner getPlannerByUser(User user) {
        return plannerRepository.findByUser(user);
    }

    private User getUserByKakaoId(CustomOAuth2User user) {
        return userRepository.findByKakaoId(user.getId()).orElseThrow(IllegalArgumentException::new);
    }

    private Planner getPlannerById(Long plannerId) {
        return plannerRepository.findById(plannerId).orElseThrow(IllegalArgumentException::new);
    }

    private Planner getPlannerByKakaoId(CustomOAuth2User user) {
        return plannerRepository.findByUser_KakaoId(user.getId());
    }

    private User getUserByPlannerId(Long plannerId) {
        return plannerRepository.findUserByPlannerId(plannerId);
    }

    private RecommendPlanner getLatestRecommendPlanner() {
        return recommendPlannerRepository.findFirstByOrderByTimeDesc();
    }

    private List<PlannerDto> getPlannerDtosFromList(String list) {
        List<PlannerDto> plannerDtos = new ArrayList<>();
        String[] s = list.split(",");
        for (int i = 0; i < 5 && !s[i].equals("A"); i++) {
            Planner planner = plannerRepository.findById(Long.parseLong(s[i]))
                    .orElseThrow(() -> new IllegalArgumentException("Planner not found"));
            plannerDtos.add(modelMapper.map(planner, PlannerDto.class));
        }
        return plannerDtos;
    }

    private List<PlannerDto> getPlannerDtosFromTopRecommendations() {
        List<Planner> planners = plannerRepository.findAllPermitPlanner();
        List<Recommend> recommends = planners.stream()
                .map(planner -> new Recommend(planner.getReview().size(), planner.getRecommendRating(), modelMapper.map(planner, PlannerDto.class)))
                .sorted()
                .collect(Collectors.toList());

        List<PlannerDto> plannerDtos = recommends.stream()
                .map(Recommend::getPlannerDto)
                .collect(Collectors.toList());

        String s = recommends.stream()
                .limit(5)
                .map(recommend -> String.valueOf(recommend.getPlannerDto().getId()))
                .collect(Collectors.joining(","));

        s += ",A,A,A,A,A";

        RecommendPlanner created = RecommendPlanner.builder()
                .time(Timestamp.from(Instant.now()))
                .list(s)
                .build();
        recommendPlannerRepository.save(created);

        return plannerDtos;
    }

    private PlannerDto createPlanner(User user, PlannerDto plannerDto) {
        Planner planner = Planner.builder()
                .user(user)
                .certificate(plannerDto.getCertificate())
                .company(plannerDto.getCompany())
                .kakaoLink(plannerDto.getKakaoLink())
                .profile(plannerDto.getProfile())
                .status("enroll")
                .plannerNickname(plannerDto.getPlannerNickname())
                .build();

        return modelMapper.map(plannerRepository.save(planner),PlannerDto.class);
    }

    private RecommendPlanner createAndSaveRecommendPlanner(List<PlannerDto> plannerDtos) {
        String s = plannerDtos.stream()
                .limit(5)
                .map(dto -> String.valueOf(dto.getId()))
                .collect(Collectors.joining(","));

        s += ",A,A,A,A,A";

        Timestamp time = Timestamp.from(Instant.now());
        RecommendPlanner created = RecommendPlanner.builder()
                .time(time)
                .list(s)
                .build();

        return recommendPlannerRepository.save(created);
    }

    private Map<String, String> saveImages(MultipartFile profileImage, MultipartFile certificateImage) throws IOException {
        String path = "classpath:/static/img/promotion";
        String profilePath = "";
        String certificatePath = "";
        if (profileImage != null && !profileImage.isEmpty()) {
            profilePath = fileHandler.uploadFile(profileImage, path);
        }
        if (certificateImage != null && !certificateImage.isEmpty()) {
            certificatePath = fileHandler.uploadFile(certificateImage, path);
        }
        Map<String, String> imagePathMap = new HashMap<>();
        imagePathMap.put("profilePath", profilePath);
        imagePathMap.put("certificatePath", certificatePath);

        return imagePathMap;
    }

    private boolean isValidRecommendPlanner(RecommendPlanner recommendPlanner) {
        if (recommendPlanner == null) return false;

        Instant beforeIns = recommendPlanner.getTime().toInstant();
        Instant nowIns = Instant.now();
        long hour = Math.abs(Duration.between(beforeIns, nowIns).toHours());
        return hour < 1;
    }

    @Getter
    @AllArgsConstructor
    static class Recommend implements Comparable<Recommend> {
        int cnt;
        int score;
        PlannerDto plannerDto;

        @Override
        public int compareTo(Recommend o) {
            if (this.score == o.score) return o.cnt - this.cnt;
            else return o.score - this.score;
        }
    }
}

