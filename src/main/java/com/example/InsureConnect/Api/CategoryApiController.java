package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.CategoryDto;
import com.example.InsureConnect.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

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
