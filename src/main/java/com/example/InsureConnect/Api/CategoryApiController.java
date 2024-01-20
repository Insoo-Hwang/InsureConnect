package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.CategoryDto;
import com.example.InsureConnect.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;
    @GetMapping("/api/category")
    public ResponseEntity<List<CategoryDto>> allCategory() {
        List<CategoryDto> categoryDtoList = categoryService.showAll();
        return ResponseEntity.ok(categoryDtoList);
    }

    @PostMapping("/api/category/new")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto categoryDto){
        CategoryDto created = categoryService.create(categoryDto);
        System.out.println("created.getCategoryName() = " + created.getCategoryName());
        System.out.println("created.getId() = " + created.getId());
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @DeleteMapping("/api/category/{categoryId}")
    private ResponseEntity<CategoryDto> delete(@PathVariable Long categoryId){
        CategoryDto deleted = categoryService.delete(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}
