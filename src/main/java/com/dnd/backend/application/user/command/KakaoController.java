package com.dnd.backend.application.user.command;

import com.dnd.backend.application.user.dto.KakaoTokenResponseDto;
import com.dnd.backend.application.user.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;
    //    @GetMapping("/kakao_login")
//    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) {
//        String accessToken = kakaoService.getAccessTokenFromKakao(code);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    @GetMapping("/kakao_login")
    public KakaoTokenResponseDto kakaoLogin(@RequestParam("code") String code) {
        KakaoTokenResponseDto accessToken = kakaoService.getAccessTokenFromKakao(code);
        return accessToken;
    }
}