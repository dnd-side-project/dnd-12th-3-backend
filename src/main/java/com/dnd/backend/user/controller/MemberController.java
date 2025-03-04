package com.dnd.backend.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.backend.user.dto.CreateAddressDTO;
import com.dnd.backend.user.entity.MemberEntity;
import com.dnd.backend.user.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService userService;

	// 내 정보 조회
	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser() {
		MemberEntity memberEntity = userService.getCurrentMember();
		return ResponseEntity.ok(memberEntity);
	}

	// 회원탈퇴
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteAccount() {
		String message = userService.deleteAccount();
		return ResponseEntity.ok(message);
	}

	@GetMapping("/all")
	public ResponseEntity<List<MemberEntity>> getAllUsers() {
		List<MemberEntity> memberEntities = userService.getAllUsers();
		return ResponseEntity.ok(memberEntities);
	}

	@PostMapping("/address")
	public ResponseEntity<?> addAddress(@RequestBody CreateAddressDTO createAddressDTO) {
		String message = userService.addAddress(createAddressDTO);
		return ResponseEntity.ok(message);
	}

	@DeleteMapping("/address/{addressId}")
	public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
		String message = userService.deleteAddress(addressId);
		return ResponseEntity.ok(message);
	}

	@GetMapping("/address")
	public ResponseEntity<?> getMyAddress() {
		return ResponseEntity.ok(userService.getMyAddress());
	}
}
