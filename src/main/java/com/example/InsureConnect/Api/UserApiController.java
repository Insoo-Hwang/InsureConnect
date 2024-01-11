package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    //유저 정보 수정
    @PatchMapping("/api/user/{userId}")
    public ResponseEntity<UserDto> updateAttribute(@PathVariable UUID userId, @RequestBody UserDto userDto){
        UserDto updated = userService.update(userId, userDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}
