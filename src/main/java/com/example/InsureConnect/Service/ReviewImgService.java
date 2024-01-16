package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.ReviewImgDto;
import com.example.InsureConnect.Repository.ReviewImgRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewImgService {
    private final ReviewImgRepository reviewImgRepository;
    private final ModelMapper modelMapper;

    public List<ReviewImgDto> findByReviewId(Long reviewId) {

        return reviewImgRepository.findByReviewId(reviewId).stream()
                .map(reviewImg -> modelMapper.map(reviewImg, ReviewImgDto.class))
                .collect(Collectors.toList());

    }
}
