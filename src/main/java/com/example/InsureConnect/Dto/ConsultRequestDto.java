package com.example.InsureConnect.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsultRequestDto {
    private Long id;

    private Long plannerId;

    private String userNickname;

    private UUID chatRoomId;

    private int status;

    private Timestamp requestTime;
}
