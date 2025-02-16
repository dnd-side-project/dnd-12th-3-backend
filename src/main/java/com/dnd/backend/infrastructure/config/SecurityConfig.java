package com.dnd.backend.infrastructure.config;


import com.dnd.backend.application.user.OAuth2SuccessHandler;
import com.dnd.backend.application.user.service.CustomOAuth2UserService;
import com.dnd.backend.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // 로그인/콜백 관련 URL은 누구나 접근 가능
                        .requestMatchers("/", "/h2-console/**", "/oauth2/**", "/login/**").permitAll()
                        // 나머지는 인증 필요
                        .anyRequest().authenticated()
                )

                // H2 콘솔 사용을 위한 설정(테스트용)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oAuth2SuccessHandler)
                )

                // JWT 필터 등록 (UsernamePasswordAuthenticationFilter 전에)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}