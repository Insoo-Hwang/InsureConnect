package com.example.InsureConnect.Config.OAuth;

import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public CustomOAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Long id = (Long) attributes.get("id");

        CustomOAuth2User customOAuth2User = CustomOAuth2User.kakao(id, attributes);
        String nickname = customOAuth2User.getNickname();
        String email = customOAuth2User.getEmail();

        List<GrantedAuthority> authorities;
        if(userRepository.findByKakaoId(id).isEmpty()){
            UserDto newDto = new UserDto(null, id, nickname, email, null, 0, "user",null,null,null);
            User user = userRepository.save(modelMapper.map(newDto, User.class));
            authorities = (List<GrantedAuthority>) user.getAuthorities();
        }
        else{
            User user = userRepository.findByKakaoId(id).orElseThrow(() -> new IllegalArgumentException());
            authorities = (List<GrantedAuthority>) user.getAuthorities();
        }

        return new CustomOAuth2User(authorities, attributes, "id", id, nickname, email);
    }
}
