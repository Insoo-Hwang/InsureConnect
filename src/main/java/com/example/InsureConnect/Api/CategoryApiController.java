package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.CategoryDto;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Service.CategoryService;
import com.example.InsureConnect.Service.ConnectCategoryService;
import com.example.InsureConnect.Service.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;
    private final ConnectCategoryService connectCategoryService;
    private final PlannerService plannerService;
    @GetMapping("/api/category")
    public ResponseEntity<List<CategoryDto>> allCategory() {
        List<CategoryDto> categoryDtoList = categoryService.showAll();
        return ResponseEntity.ok(categoryDtoList);
    }

    @PostMapping("/api/connectCategory/save")
    public void saveCategory(@AuthenticationPrincipal CustomOAuth2User user,
                               @RequestParam("category_id") List<Long> categoryList) {
        UserDto userById = plannerService.findUserById(user.getId());
        connectCategoryService.saveCategory(categoryList,userById.getPlanner().getId());
    }

    @PostMapping("/api/category/new")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto categoryDto){
        CategoryDto created = categoryService.create(categoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @DeleteMapping("/api/category/{categoryId}")
    private ResponseEntity<CategoryDto> delete(@PathVariable Long categoryId){
        CategoryDto deleted = categoryService.delete(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}
