package com.example.InsureConnect.Config;

import com.example.InsureConnect.Service.OAuth2UserService;
import com.example.InsureConnect.Service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final OAuth2UserService oAuth2UserService;

    public SecurityConfig(OAuth2UserService oAuth2UserService) {
        this.oAuth2UserService = oAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.oauth2Login(oauth2Configurer -> oauth2Configurer
                .loginPage("/")
                .defaultSuccessUrl("/")
                .userInfoEndpoint()
                .userService(oAuth2UserService))
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
        http.authorizeHttpRequests((authorizeRequests) ->
                    authorizeRequests
                            .requestMatchers("/management/**").hasAnyAuthority("ADMIN")
                            .requestMatchers("/mypage", "/chat").hasAnyAuthority("USER", "PLANNER")
                            .anyRequest().permitAll()
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailService userDetailService) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .and()
                .build();
    }
}