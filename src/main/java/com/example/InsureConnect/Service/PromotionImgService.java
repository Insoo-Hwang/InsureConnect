package com.example.InsureConnect.Service;


import com.example.InsureConnect.Dto.PromotionImgDto;
import com.example.InsureConnect.Repository.PromotionImgRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PromotionImgService {
    private final PromotionImgRepository promotionImgRepository;
    private final ModelMapper modelMapper;
    public List<PromotionImgDto> findByPromotionId(Long promotionId) {
        return promotionImgRepository.findByPromotionId(promotionId).stream()
                .map(promotionImg -> modelMapper.map(promotionImg, PromotionImgDto.class))
                .collect(Collectors.toList());
    }

}
