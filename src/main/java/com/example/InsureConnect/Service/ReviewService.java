package com.example.InsureConnect.Service;

import com.example.InsureConnect.Config.OAuth.CustomOAuth2User;
import com.example.InsureConnect.Dto.ReviewDto;
import com.example.InsureConnect.Dto.ReviewImgDto;
import com.example.InsureConnect.Entity.*;
import com.example.InsureConnect.Repository.PlannerRepository;
import com.example.InsureConnect.Repository.ReviewImgRepository;
import com.example.InsureConnect.Repository.ReviewRepository;
import com.example.InsureConnect.Repository.UserRepository;
import com.example.InsureConnect.handler.FileHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final UserRepository userRepository;
    private final PlannerRepository plannerRepository;
    private final FileHandler fileHandler;
    private final ModelMapper modelMapper;

    public void saveReview(ReviewDto reviewDto,  List<MultipartFile> images, CustomOAuth2User user, Long planner_id) throws IOException {
        String path = "classpath:/static/img/review";
        User byUser = userRepository.findByKakaoId(user.getId()).orElseThrow(IllegalArgumentException::new);
        Planner byPlanner = plannerRepository.findById(planner_id).orElseThrow(IllegalArgumentException::new);

        Review review = Review.builder()
                .planner(byPlanner)
                .user(byUser)
                .rate(reviewDto.getRate())
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .edit(null)
                .build();

        review.setWriteToCurrentTime();

        reviewRepository.save(review);

        List<String> imgLinks = fileHandler.uploadFiles(images, path);
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
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
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

    public Page<ReviewDto> findAll(Pageable pageable){
        Page<Review> reviews = reviewRepository.findAll(pageable);
        return reviews.map(review -> modelMapper.map(review, ReviewDto.class));
    }

    public List<ReviewDto> findAll(){
        List<Review> reviewList = reviewRepository.findAll();
        return reviewList.stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toList());
    }

    public ReviewDto findById(Long reviewId) {
        Review byId = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with id: " + reviewId));
        ReviewDto reviewDto = modelMapper.map(byId, ReviewDto.class);
        List<ReviewImgDto> reviewImgDtoList = reviewDto.getReviewImg();

        if (reviewImgDtoList != null) {
            List<ReviewImgDto> sortedReviewImgDto = reviewImgDtoList.stream()
                    .sorted(Comparator.comparingInt(ReviewImgDto::getSequence))
                    .collect(Collectors.toList());

            reviewDto.setReviewImg(sortedReviewImgDto);
        }
        return reviewDto;
    }


    public List<ReviewDto> findByPlannerId(Long plannerId) {
        return reviewRepository.findByPlannerId(plannerId).stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toList());
    }
}
