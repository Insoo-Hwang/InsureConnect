package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDto findById(UUID id){
        Optional<User> byId = userRepository.findById(id);
        return byId.map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    public UserDto findByKakaoId(Long kakaoId){
        Optional<User> byKakaoId = userRepository.findByKakaoId(kakaoId);
        return byKakaoId.map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new IllegalArgumentException("User not found with byKaKaoId: " + byKakaoId));
    }

    @Transactional
    public UserDto update(UUID id, UserDto userDto){
        User target = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        target.patch(userDto);
        User updated = userRepository.save(target);
        return modelMapper.map(updated, UserDto.class);
    }

    public List<UserDto> showAll(){
        List<User> users = userRepository.findByNotNull();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto deleteUser(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());
        user.delete();
        User deleted = userRepository.save(user);
        return modelMapper.map(deleted, UserDto.class);
    }
}
