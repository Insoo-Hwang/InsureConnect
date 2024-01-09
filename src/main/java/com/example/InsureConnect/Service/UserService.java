package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserDto findById(UUID id){
        return UserDto.toDto(userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException()));
    }

    public UserDto findByKakaoId(Long kakaoId){
        return UserDto.toDto(userRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new IllegalArgumentException()));
    }
}
