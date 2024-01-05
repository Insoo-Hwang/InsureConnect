package com.example.InsureConnect.Entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {
    private Map<String, Object> attributes;
    private Long id;
    private String nickname;

    @Builder
    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            Long id, String nickname) {
        super(authorities, attributes, nameAttributeKey);
        this.attributes = attributes;
        this.id = id;
        this.nickname = nickname;
    }

    public static CustomOAuth2User kakao(Long id, Map<String, Object> attributes){
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return CustomOAuth2User.builder()
                .nickname((String) kakaoProfile.get("nickname"))
                .attributes(attributes)
                .nameAttributeKey("id")
                .id(id)
                .build();
    }
}