package com.dnd.backend.application.mypage.dto;


import com.dnd.backend.domain.loginEnum.Gender;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMypageResponseDto {
    private String name;

    private String email;

    private Gender gender;

    private int age;

    private String phoneNumber;

    //소속, 부서/학과, 직급
    private String company;
    private String department;
    private String position;

//    //커리어 키워드
//    private KeywordName[] keywordName;

    //관심 지역
    private String city;
    private String addressLine;
}