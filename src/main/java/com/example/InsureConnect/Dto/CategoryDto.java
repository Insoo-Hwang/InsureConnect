package com.example.InsureConnect.Dto;

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

    private List<Long> connectCategoryId;
}
