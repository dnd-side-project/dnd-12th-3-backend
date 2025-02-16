package com.dnd.backend.application.user;

import com.dnd.backend.domain.User;
import com.dnd.backend.infrastructure.persistence.user.UserRepository;
import com.dnd.backend.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 카카오 ID
        String kakaoId = String.valueOf(oAuth2User.getAttributes().get("id"));
        Optional<User> optionalUser = userRepository.findByKakaoId(kakaoId);
        if (optionalUser.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "카카오 로그인 실패");
            return;
        }

        User user = optionalUser.get();
        // JWT 생성
        String token = jwtProvider.createToken(user.getKakaoId(), user.getEmail());

        // 예: 로컬호스트 3000으로 리다이렉트하면서 토큰 전달
        // 실제론 HTTPS + 쿠키/헤더 등에 담는 방법 권장
        String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/login/success")
                .queryParam("token", token)
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }
}