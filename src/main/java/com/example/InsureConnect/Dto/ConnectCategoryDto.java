package com.example.InsureConnect.Dto;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ConnectCategoryDto {

    private Long id;

    @JsonManagedReference
    private PlannerDto planner;

    @JsonManagedReference
    private CategoryDto category;
}
