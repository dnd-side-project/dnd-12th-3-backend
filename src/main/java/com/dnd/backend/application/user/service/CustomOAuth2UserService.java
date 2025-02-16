package com.dnd.backend.application.user.service;

import com.dnd.backend.domain.User;
import com.dnd.backend.infrastructure.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        // Spring Security 기본 구현체
        OAuth2User oAuth2User = new org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService()
                .loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        if (!"kakao".equals(registrationId)) {
            throw new IllegalArgumentException("카카오 이외의 OAuth는 지원하지 않습니다.");
        }

        // 카카오 사용자 정보
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String kakaoId = String.valueOf(attributes.get("id"));

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = null;
        String kakaoNickname = null;
        String profileImageUrl = null;

        if (kakaoAccount != null) {
            email = (String) kakaoAccount.get("email");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            if (profile != null) {
                kakaoNickname = (String) profile.get("nickname");
                profileImageUrl = (String) profile.get("profile_image_url");
            }
        }

        // DB 조회
        Optional<User> optionalUser = userRepository.findByKakaoId(kakaoId);
        User user;
        if (optionalUser.isEmpty()) {
            // 신규 회원가입
            user = User.builder()
                    .kakaoId(kakaoId)
                    .email(email)
                    .kakaoNickname(kakaoNickname)
                    .profileImageUrl(profileImageUrl)
                    .build();
            userRepository.save(user);
        } else {
            // 기존 회원 → 로그인 시 갱신
            user = optionalUser.get();
            user.setKakaoNickname(kakaoNickname);
            user.setProfileImageUrl(profileImageUrl);
            userRepository.save(user);
        }

        // Security 내부적으로 사용하는 OAuth2User 반환
        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                oAuth2User.getAttributes(),
                "id"  // user-name-attribute
        );
    }
}