package com.dnd.backend.application.mypage.dto;

import com.dnd.backend.domain.loginEnum.Gender;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class MyPageResponseDto {

    private String name;

    @Email
    private String email;

    // 전화번호는 회원가입 3 화면에서 저장되는 값이기 때문에 nullable이면 insert 과정에서 오류 발생 -> null 허용으로 수정함
    private String phoneNumber;

    // 회원가입 3 화면 (대략 6가지)
    private Gender gender;

    private int age;

    // 관심지역
    private String addressLine;

    // 소속(회사/기관/학교명)
    private String company;

    // 부서 및 학과
    private String department;

    // 직급
    private String position;

//    private List<KeywordName> keywordName;
}