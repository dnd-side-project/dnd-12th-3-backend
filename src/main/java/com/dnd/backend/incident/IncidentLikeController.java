package com.dnd.backend.incident;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.backend.incident.application.ToggleLikeIncidentUsecase;
import com.dnd.backend.incident.service.IncidentLikeReadService;
import com.dnd.backend.user.security.CustomeUserDetails;
import com.dnd.backend.user.security.customAuthenticationPrincipal.AuthUser;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/incidents")

@RestController
@RequiredArgsConstructor
public class IncidentLikeController {

	private final IncidentLikeReadService incidentLikeReadService;
	private final ToggleLikeIncidentUsecase toggleLikeIncidentUsecase;

	@PostMapping("/{incidentId}/likes")
	public String toggleLike(@PathVariable Long incidentId,
		@AuthUser CustomeUserDetails user) {
		Long userId = user.getId();
		return toggleLikeIncidentUsecase.execute(userId, incidentId);
	}

	/**
	 * 현재 좋아요 수 카운팅 API
	 * 요청 예: GET /api/incidents/1/likes/count
	 */
	@GetMapping("/{incidentId}/likes/count")
	public ResponseEntity<Integer> getLikeCount(@PathVariable Long incidentId) {
		int likeCount = incidentLikeReadService.getLikeCount(incidentId);
		return ResponseEntity.ok(likeCount);
	}

	@GetMapping("/{incidentId}/likes/check")
	public ResponseEntity<Boolean> hasUserLiked(
		@PathVariable Long incidentId,
		@RequestParam Long writerId) {
		boolean hasLiked = incidentLikeReadService.hasUserLiked(writerId, incidentId);
		return ResponseEntity.ok(hasLiked);
	}
}
