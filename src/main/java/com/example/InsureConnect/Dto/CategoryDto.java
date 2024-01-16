package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.ConnectCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {

    private Long id;

    private String categoryName;

    @JsonBackReference
    private ConnectCategory connectCategory;
}
