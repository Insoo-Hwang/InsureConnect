package com.example.InsureConnect.Dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlannerDto {

    private Long id;

    private String profile;

    private String company;

    private String certificate;

    private String status;

    private MultipartFile[] f = new MultipartFile[2];

    public PlannerDto toDto(Long id, String profile, String company, String certificate, String status) {
        this.id = id;
        this.profile = profile;
        this.company = company;
        this.certificate = certificate;
        this.status = status;
        return this;
    }
}
