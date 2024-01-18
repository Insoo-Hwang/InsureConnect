package com.example.InsureConnect.Config;

import com.example.InsureConnect.Dto.CategoryDto;
import com.example.InsureConnect.Dto.ConnectCategoryDto;
import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Entity.Category;
import com.example.InsureConnect.Entity.ConnectCategory;
import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.Review;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
//
        //Planner -> PlannerDto TypeMap
        modelMapper.createTypeMap(Planner.class, PlannerDto.class)
                .addMappings(mapping -> {
                    mapping.map(src -> src.getUser().getNickname(), PlannerDto::setNickname);
                    mapping.map(src -> src.getPromotion().getTitle(), PlannerDto::setTitle);
                    mapping.map(src -> src.getPromotion().getContent(), PlannerDto::setContent);
                    mapping.map(src -> src.getPromotion().getWrite(), PlannerDto::setWrite);
                    mapping.using(convertReviewToInt()).map(src -> src.getReview(), PlannerDto::setRate);
                    mapping.using(convertConnectCategoryToStringList()).map(src -> src.getConnectCategory(), PlannerDto::setCategoryName);

                });

        //ConnectCategory -> ConnectCategoryDto TypeMap
        modelMapper.createTypeMap(ConnectCategory.class, ConnectCategoryDto.class)
                .addMappings(mapping -> {
                    mapping.map(src -> src.getCategory().getId(), ConnectCategoryDto::setCategoryId);
                    mapping.map(src -> src.getPlanner().getId(), ConnectCategoryDto::setPlannerId);
                });

        //Category -> CategoryDto TypeMap
        TypeMap<Category, CategoryDto> categoryTypeMap = modelMapper.createTypeMap(Category.class, CategoryDto.class);
        categoryTypeMap.addMappings(mapping -> {
            mapping.using(convertConnectCategoryToLong()).map(src -> src.getConnectCategory(), CategoryDto::setConnectCategoryId);
        });

        return modelMapper;
    }

    private Converter<List<ConnectCategory>, List<Long>> convertConnectCategoryToLong() {
        return context -> context.getSource()
                .stream()
                .map(ConnectCategory::getId)
                .collect(Collectors.toList());
    }

    private Converter<List<ConnectCategory>, List<String>> convertConnectCategoryToStringList() {
        return context -> context.getSource()
                .stream()
                .map(connectCategory -> connectCategory.getCategory().getCategoryName())
                .collect(Collectors.toList());
    }

    private Converter<List<Review>, List<Integer>> convertReviewToInt() {
        return context -> context.getSource()
                .stream()
                .map(Review::getRate)
                .collect(Collectors.toList());
    }

}
