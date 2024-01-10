package com.example.InsureConnect.Service;

import com.example.InsureConnect.Repository.ReviewImgRepository;
import com.example.InsureConnect.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
}
