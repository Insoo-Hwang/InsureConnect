package com.example.InsureConnect.Dto;

import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public class QuestionRequestDto implements Serializable {
    private Long id;
    private String question;
}