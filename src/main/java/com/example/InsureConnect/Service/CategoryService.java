package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.CategoryDto;
import com.example.InsureConnect.Entity.Category;
import com.example.InsureConnect.Repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<CategoryDto> showAll(){
        List<Category> categoryList = categoryRepository.findAll();
        return modelMapper.map(categoryList, new TypeToken<List<CategoryDto>>() {}.getType());
    }

    @Transactional
    public CategoryDto create(CategoryDto dto){
        Category category = Category.toCategory(dto);
        Category created = categoryRepository.save(category);
        return modelMapper.map(created, CategoryDto.class);
    }

    @Transactional
    public CategoryDto delete(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDto.class);
    }
}
