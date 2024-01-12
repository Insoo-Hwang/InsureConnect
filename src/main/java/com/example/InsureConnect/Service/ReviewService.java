package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.ReviewDto;
import com.example.InsureConnect.Entity.Review;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.Repository.ReviewImgRepository;
import com.example.InsureConnect.Repository.ReviewRepository;
import com.example.InsureConnect.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final UserRepository userRepository;

    public List<ReviewDto> findByUserId(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());
        List<ReviewDto> dto = reviewRepository.findByUser(user)
                .stream()
                .map(review -> ReviewDto.toDto(review))
                .collect(Collectors.toList());
        return dto;
    }

    @Transactional
    public ReviewDto delete(Long reviewId){
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException());
        reviewRepository.delete(review);
        return ReviewDto.toDto(review);
    }

    public List<ReviewDto> findAll(){
        return reviewRepository.findAll()
                .stream()
                .map(review -> ReviewDto.toDto(review))
                .collect(Collectors.toList());
    }
}
