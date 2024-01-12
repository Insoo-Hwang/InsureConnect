package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.User;
import lombok.*;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlannerDto {

    private Long id;

    private UUID userId;

    private String profile;

    private String company;

    private String certificate;

    private String status;

    private MultipartFile[] f = new MultipartFile[2];

    public static PlannerDto toDto(Planner planner) {
        return new PlannerDto(planner.getId(),planner.getUser().getId(), planner.getProfile(), planner.getCompany(), planner.getCertificate(), planner.getStatus(), null);
    }

}
