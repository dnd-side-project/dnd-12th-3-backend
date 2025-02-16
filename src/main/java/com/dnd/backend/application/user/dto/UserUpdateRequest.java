package com.dnd.backend.application.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    // 우리 서비스에서 사용하는 닉네임
    private String userNickname;
    // 최대 3개의 지역
    private String region1;
    private String region2;
    private String region3;
}
