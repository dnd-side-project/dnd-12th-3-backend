package com.dnd.backend.jwt;


import com.dnd.backend.application.user.CustomUserPrincipal;
import com.dnd.backend.domain.User;
import com.dnd.backend.infrastructure.persistence.user.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = resolveToken(request);
        if (token != null && jwtProvider.validateToken(token)) {
            try {
                String kakaoId = jwtProvider.getKakaoIdFromToken(token);

                // DB에서 사용자 조회
                User user = userRepository.findByKakaoId(kakaoId).orElse(null);
                if (user != null) {
                    // CustomUserPrincipal 생성
                    CustomUserPrincipal principal = new CustomUserPrincipal(
                            user.getId(),
                            user.getKakaoId(),
                            user.getEmail()
                    );

                    // Authentication 객체 생성
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    principal, // Principal
                                    null,      // Credentials(패스워드 없음)
                                    principal.getAuthorities()
                            );

                    // SecurityContext에 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException e) {
                // 토큰 파싱 중 오류
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}