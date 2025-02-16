package com.dnd.backend.application.user.command;


import com.dnd.backend.application.user.CustomUserPrincipal;
import com.dnd.backend.application.user.dto.UserUpdateRequest;
import com.dnd.backend.domain.User;
import com.dnd.backend.infrastructure.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class KakaoController {

    private final UserRepository userRepository;

    /**
     * 현재 로그인한 사용자 정보 조회
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal CustomUserPrincipal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("인증되지 않은 사용자입니다.");
        }
        // DB에서 사용자 조회
        User user = userRepository.findByKakaoId(principal.getKakaoId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return ResponseEntity.ok(user);
    }

    /**
     * 닉네임 / 지역 업데이트
     */
    @PutMapping("/me")
    public ResponseEntity<?> updateMyInfo(@AuthenticationPrincipal CustomUserPrincipal principal,
                                          @RequestBody UserUpdateRequest request) {
        if (principal == null) {
            return ResponseEntity.status(401).body("인증되지 않은 사용자입니다.");
        }

        // DB에서 사용자 조회
        User user = userRepository.findByKakaoId(principal.getKakaoId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 업데이트
        user.setUserNickname(request.getUserNickname());
        user.setRegion1(request.getRegion1());
        user.setRegion2(request.getRegion2());
        user.setRegion3(request.getRegion3());

        userRepository.save(user);

        return ResponseEntity.ok("사용자 정보가 업데이트되었습니다.");
    }
}