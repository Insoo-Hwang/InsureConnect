package com.example.InsureConnect.Config;

import com.example.InsureConnect.Config.OAuth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.example.InsureConnect.Config.OAuth.OAuth2SuccessHandler;
import com.example.InsureConnect.Config.jwt.TokenProvider;
import com.example.InsureConnect.Repository.RefreshTokenRepository;
import com.example.InsureConnect.Config.OAuth.OAuth2UserService;
import com.example.InsureConnect.Service.UserDetailService;
import com.example.InsureConnect.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.oauth2Login(oauth2Configurer -> oauth2Configurer
                .loginPage("/")
                .authorizationEndpoint()
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .successHandler(oAuth2SuccessHandler())
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
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService,
                modelMapper
        );
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailService userDetailService) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .and()
                .build();
    }
}