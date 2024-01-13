package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.ReviewDto;
import com.example.InsureConnect.Entity.*;
import com.example.InsureConnect.Handler.FileUploadHandler;
import com.example.InsureConnect.Repository.PlannerRepository;
import com.example.InsureConnect.Repository.ReviewImgRepository;
import com.example.InsureConnect.Repository.ReviewRepository;
import com.example.InsureConnect.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final UserRepository userRepository;
    private final PlannerRepository plannerRepository;
    private final FileUploadHandler fileUploadHandler;
    private final ModelMapper modelMapper;

    public void saveReview(ReviewDto reviewDto, MultipartFile[] images, CustomOAuth2User user,Long planner_id) throws IOException {
        String path = "classpath:/static/img/review";
        Optional<User> byUser = userRepository.findByKakaoId(user.getId());
        Optional<Planner> byPlanner = plannerRepository.findById(planner_id);

        Review review = Review.builder()
                .planner(byPlanner.get())
                .user(byUser.get())
                .rate(reviewDto.getRate())
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .edit(null)
                .build();

        review.setWriteToCurrentTime();

        reviewRepository.save(review);

        List<String> imgLinks = fileUploadHandler.uploadFiles(images, path);
        List<ReviewImg> reviewImgs = new ArrayList<>();

        for (int i = 0; i < imgLinks.size(); i++) {
            ReviewImg reviewImg = ReviewImg.builder()
                    .review(review)
                    .imgLink(imgLinks.get(i))
                    .sequence(i + 1)
                    .build();
            reviewImgs.add(reviewImg);
        }
        reviewImgRepository.saveAll(reviewImgs);

    }

    public List<ReviewDto> findByUserId(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());
        List<Review> reviews = reviewRepository.findByUser(user);
              return   reviews.stream()
                .map(review -> modelMapper.map(review,ReviewDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDto delete(Long reviewId){
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException());
        reviewRepository.delete(review);
        return modelMapper.map(review, ReviewDto.class);
    }

    public List<ReviewDto> findAll(){
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toList());
    }
}
