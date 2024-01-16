package com.example.InsureConnect.Service;

import com.example.InsureConnect.Entity.Category;
import com.example.InsureConnect.Entity.ConnectCategory;
import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Repository.CategoryRepository;
import com.example.InsureConnect.Repository.ConnectCategoryRepository;
import com.example.InsureConnect.Repository.PlannerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConnectCategoryService {
    private final ConnectCategoryRepository connectCategoryRepository;
    private final PlannerRepository plannerRepository;
    private final CategoryRepository categoryRepository;

    public void saveCategory(List<Long> categoryIdList, Long plannerId) {
        // Planner 엔티티 가져오기
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new EntityNotFoundException("Planner not found with id: " + plannerId));

        // Category 엔티티 목록 가져오기
        List<Category> categories = categoryRepository.findAllById(categoryIdList);

        // ConnectCategoryDto 엔티티 생성 및 저장
        List<ConnectCategory> connectCategories = new ArrayList<>();
        for (Category category : categories) {
            ConnectCategory connectCategory = ConnectCategory.builder()
                    .planner(planner)
                    .category(category)
                    .build();
            connectCategories.add(connectCategory);
        }

        connectCategoryRepository.saveAll(connectCategories);
    }

}
