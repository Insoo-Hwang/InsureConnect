package com.example.InsureConnect.Config;

import com.example.InsureConnect.Dto.*;
import com.example.InsureConnect.Entity.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        //User -> UserDto TypeMap
        modelMapper.createTypeMap(User.class, UserDto.class)
                .addMappings(mapping -> {
                    mapping.map(src -> src.getPlanner().getId(), UserDto::setPlannerId);
                    mapping.using(convertReviewToLongList()).map(User::getReview, UserDto::setReviewId);
                    mapping.using(convertChatRoomToLongList()).map(User::getChatRooms, UserDto::setChatRoomId);
                });


        //ChatRoom -> ChatRoomDto TypeMap
        modelMapper.createTypeMap(ChatRoom.class, ChatRoomDto.class)
                        .addMappings(mapping -> {
                            mapping.using(convertChatToLongList()).map(ChatRoom::getChats, ChatRoomDto::setChatId);
                        });

        //Planner -> PlannerDto TypeMap
        modelMapper.createTypeMap(Planner.class, PlannerDto.class)
                .addMappings(mapping -> {
                    mapping.map(src -> src.getUser().getNickname(), PlannerDto::setNickname);
                    mapping.using(convertConnectCategoryToStringList()).map(Planner::getConnectCategory, PlannerDto::setCategoryName);

                });

        //Promotion -> PromotionDto TypeMap
        modelMapper.createTypeMap(Promotion.class, PromotionDto.class)
                .addMappings(mapping -> {
                    mapping.map(src -> src.getPlanner().getId(), PromotionDto::setPlannerId);
                });

        //PromotionImg -> PromotionImgDto TypeMap
        modelMapper.createTypeMap(PromotionImg.class, PromotionImgDto.class)
                .addMappings(mapping -> {
                    mapping.map(src -> src.getPromotion().getId(), PromotionImgDto::setPromotionId);
                });

        //Review -> ReviewDto TypeMap
        modelMapper.createTypeMap(Review.class, ReviewDto.class)
                .addMappings(mapping -> {
                    mapping.map(src -> src.getPlanner().getPlannerNickname(), ReviewDto::setPlannerNickname);
                });

        //ReviewImg -> ReviewImgDto TypeMap
        modelMapper.createTypeMap(ReviewImg.class, ReviewImgDto.class)
                .addMappings(mapping -> {
                    mapping.map(src -> src.getReview().getId(), ReviewImgDto::setReviewId);
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
            mapping.using(convertConnectCategoryToLong()).map(src -> src.getConnectCategory(), CategoryDto::setConnectionId);
        });

        return modelMapper;

    }

    private Converter<List<ChatRoom>, List<Long>> convertChatRoomToLongList(){
        return context -> context.getSource()
                .stream()
                .map(ChatRoom::getId)
                .collect(Collectors.toList());
    }

    private Converter<List<Chat>,List<Long>> convertChatToLongList() {
        return context -> context.getSource()
                .stream()
                .map(Chat::getId)
                .collect(Collectors.toList());
    }

    private Converter<List<Review>, List<Long>> convertReviewToLongList() {
        return context -> context.getSource()
                .stream()
                .map(Review::getId)
                .collect(Collectors.toList());
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

}