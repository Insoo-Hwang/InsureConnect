package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Entity.User;
import com.example.InsureConnect.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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

    @Override
    public CustomOAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Long id = (Long) attributes.get("id");

        CustomOAuth2User customOAuth2User = CustomOAuth2User.kakao(id, attributes);
        String nickname = customOAuth2User.getNickname();

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

        if(userRepository.findByKakaoId(id).isEmpty()){
            UserDto newDto = new UserDto(null, id, nickname, null, 0, null,null,null,null);
            userRepository.save(User.toUser(newDto));
        }
        return new CustomOAuth2User(authorities, attributes, "id", id, nickname);
    }
}
