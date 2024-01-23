package com.example.InsureConnect.Service;

import com.example.InsureConnect.Config.jwt.TokenProvider;
import com.example.InsureConnect.Entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        UUID userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = modelMapper.map(userService.findById(userId), User.class);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }

}