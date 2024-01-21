package com.example.InsureConnect.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatRoomDto {

    private Long id;

    private UUID code;

    private UserDto userDto;

    private Timestamp timestamp;

    private List<Long> chatId;

}
