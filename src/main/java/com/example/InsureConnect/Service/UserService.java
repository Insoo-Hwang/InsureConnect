package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Transactional
    public UserDto update(UUID id, UserDto userDto){
        User target = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        target.patch(userDto);
        User updated = userRepository.save(target);
        return UserDto.toDto(updated);
    }

    public List<UserDto> showAll(){
        List<User> users = userRepository.findByNotNull();
        return users.stream()
                .map(user -> UserDto.toDto(user))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto deleteUser(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());
        user.delete();
        User deleted = userRepository.save(user);
        return UserDto.toDto(deleted);
    }
}
