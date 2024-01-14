package com.example.InsureConnect.Entity;

import com.example.InsureConnect.Dto.CategoryDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String category;

    public static Category toCategory(CategoryDto dto){
        return new Category(dto.getId(), dto.getCategory());
    }
}
