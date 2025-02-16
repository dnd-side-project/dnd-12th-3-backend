package com.dnd.backend.application.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * JWT 토큰 검증 후, SecurityContext에 저장할 사용자 정보
 */
@Getter
public class CustomUserPrincipal implements UserDetails {

    private final Long id;         // DB PK
    private final String kakaoId;  // 카카오 ID
    private final String email;    // 이메일
    // 필요하면 userNickname, region1,2,3 등 추가

    // 생성자
    public CustomUserPrincipal(Long id, String kakaoId, String email) {
        this.id = id;
        this.kakaoId = kakaoId;
        this.email = email;
    }

    /**
     * UserDetails 메서드들 구현
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 간단히 ROLE_USER 하나만 넣는 예시
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        // 소셜 로그인만 쓴다면 굳이 비밀번호가 없을 수 있음
        return null;
    }

    @Override
    public String getUsername() {
        // UserDetails가 username으로 구분하는 값 (kakaoId 등)
        return kakaoId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}