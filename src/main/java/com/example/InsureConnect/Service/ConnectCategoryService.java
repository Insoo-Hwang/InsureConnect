package com.example.InsureConnect.Service;

import com.example.InsureConnect.Entity.ConnectCategory;
import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Repository.CategoryRepository;
import com.example.InsureConnect.Repository.ConnectCategoryRepository;
import com.example.InsureConnect.Repository.PlannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConnectCategoryService {
    private final ConnectCategoryRepository connectCategoryRepository;
    private final PlannerRepository plannerRepository;
    private final CategoryRepository categoryRepository;

    public void saveCategory(List<Long> categoryIdList,Long plannerId) {
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new RuntimeException("Planner not found with id: " + plannerId));

        List<ConnectCategory> connectCategories = categoryIdList.stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .map(category -> ConnectCategory.builder()
                                .category(category)
                                .planner(planner)
                                .build())
                        .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId)))
                .collect(Collectors.toList());

        connectCategoryRepository.saveAll(connectCategories);
    }
}
