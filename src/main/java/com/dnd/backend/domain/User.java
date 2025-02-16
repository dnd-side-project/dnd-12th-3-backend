package com.dnd.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 내부 PK

    @Column(unique = true)
    private String kakaoId;          // 카카오에서 받은 유저 식별자

    private String email;            // 카카오 계정 이메일
    private String kakaoNickname;    // 카카오 프로필에 등록된 닉네임
    private String profileImageUrl;  // 카카오 프로필 이미지

    /**
     * 사용자가 직접 설정하는 닉네임
     * (카카오 프로필 닉네임과는 별개로, 우리 서비스에서만 사용하는 닉네임)
     */
    private String userNickname;

    /**
     * 최대 3개의 지역 정보를 저장
     * 예: "서울시 강남구", "부산시 해운대구" 등 텍스트 형태
     */
    private String region1;
    private String region2;
    private String region3;
}
