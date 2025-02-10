package com.dnd.backend.application.user.command;


import com.dnd.backend.application.mypage.dto.UpdateMypageResponseDto;
import com.dnd.backend.application.user.dto.CustomUserDetails;
import com.dnd.backend.application.user.dto.UserSignUpRequestDto;
import com.dnd.backend.application.user.service.UserService;
import com.dnd.backend.domain.User;
import com.dnd.backend.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // 회원가입 1, 2 (이름, 이메일, 비밀번호, 비밀번호 확인, role)
    @PostMapping("/join")
    @Transactional
    public ResponseEntity<Void> signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        User user = userService.signUp(userSignUpRequestDto);

        String token = jwtUtil.createJwt(user.getEmail(), String.valueOf(user.getRole()), 600000L);

        // 회원가입 이후 리다이렉션할 URL 생성
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/join/detail")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Location", redirectUrl);

        return new ResponseEntity<>(headers, HttpStatus.OK); //200
    }


    //화면 테스트용
    @GetMapping("/join/detail")
    @ResponseBody
    public String detail() {
        return "join detail";
    }


    //회원가입3
    @Transactional
    @PatchMapping("/join/detail")
    public ResponseEntity<Void> updateDetail(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UpdateMypageResponseDto updateMypageResponseDto) {
        try {
            userService.findUserByEmailAndUpdate(customUserDetails.getUsername(), updateMypageResponseDto);

            // 정보 저장 이후 리다이렉션할 URL 생성
            String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/login")
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", redirectUrl);
            return new ResponseEntity<>(headers, HttpStatus.FOUND);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //400
        }
    }

    //화면 테스트용
    @GetMapping("/login")
    @ResponseBody
    public String login() {
        return "login page";
    }
}