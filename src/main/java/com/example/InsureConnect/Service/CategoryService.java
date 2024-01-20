package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.CategoryDto;
import com.example.InsureConnect.Entity.Category;
import com.example.InsureConnect.Repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@org.springframework.transaction.annotation.Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<CategoryDto> showAll(){
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().filter(Objects::nonNull).map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public CategoryDto create(CategoryDto dto){
        Category category = modelMapper.map(dto, Category.class);
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
