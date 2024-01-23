package com.example.InsureConnect.Dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RecommendDto {

    List<PlannerDto> list;

    Timestamp time;
}
