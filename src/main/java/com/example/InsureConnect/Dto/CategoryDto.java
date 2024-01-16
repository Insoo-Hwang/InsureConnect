package com.example.InsureConnect.Dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {

    private Long id;

    private String categoryName;

    @JsonIgnore
    private List<ConnectCategoryDto> connectCategory;
}
