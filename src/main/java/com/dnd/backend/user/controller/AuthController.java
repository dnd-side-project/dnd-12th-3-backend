package com.dnd.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.backend.user.dto.LoginRequest;
import com.dnd.backend.user.dto.RegistrationRequest;
import com.dnd.backend.user.dto.SocialLoginRequest;
import com.dnd.backend.user.service.AuthServiceImpl;
import com.dnd.backend.user.service.SocialLoginService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthServiceImpl authService;
	private final SocialLoginService socialLoginService;

	// 일반 회원가입
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
		String message = authService.registerUser(registrationRequest);
		return ResponseEntity.ok(message);
	}

	// 일반 로그인
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authService.loginUser(loginRequest));
	}

	// 로그아웃 (JWT는 상태 없음: 클라이언트에서 토큰 삭제)
	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser() {
		authService.logoutUser();
		return ResponseEntity.ok("Logged out successfully");
	}

	// 구글 소셜 로그인 (안드로이드가 전달한 ID 토큰)
	@PostMapping("/oauth/google")
	public ResponseEntity<?> googleLogin(@RequestBody SocialLoginRequest request) {
		return ResponseEntity.ok(socialLoginService.loginWithGoogle(request));
	}

	// 카카오 소셜 로그인 (안드로이드가 전달한 Access Token)
	@PostMapping("/oauth/kakao")
	public ResponseEntity<?> kakaoLogin(@RequestBody SocialLoginRequest request) {
		return ResponseEntity.ok(socialLoginService.loginWithKakao(request));
	}
}
