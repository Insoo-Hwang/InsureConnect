package com.example.InsureConnect.Dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ConnectCategoryDto {

    private Long id;

    private Long plannerId;

    private Long categoryId;
}
