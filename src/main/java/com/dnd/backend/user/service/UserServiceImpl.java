package com.dnd.backend.user.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.backend.user.dto.AddressDTO;
import com.dnd.backend.user.entity.Address;
import com.dnd.backend.user.entity.User;
import com.dnd.backend.user.exception.BadRequestException;
import com.dnd.backend.user.exception.ResourceNotFoundException;
import com.dnd.backend.user.exception.UnauthorizedException;
import com.dnd.backend.user.repository.AddressRepository;
import com.dnd.backend.user.repository.UserRepository;
import com.dnd.backend.user.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final AddressRepository addressRepository;
	private final SecurityService securityService;

	// 현재 인증된 사용자 조회
	private User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
		return userRepository.findById(userPrincipal.getId())
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public User getCurrentUser() {
		User user = getAuthenticatedUser();
		user.setPassword(null); // 비밀번호 노출 방지
		return user;
	}

	@Override
	@Transactional
	public String deleteAccount() {
		User user = getAuthenticatedUser();
		userRepository.delete(user);
		return "User account deleted successfully";
	}

	@Transactional
	public String addAddress(AddressDTO addressDTO) {
		User user = securityService.getAuthenticatedUser();
		if (user.getAddresses().size() >= 2) {
			throw new BadRequestException("Cannot add more than 2 addresses");
		}

		Address address = Address.builder()
			.title(addressDTO.getTitle())
			.latitude(addressDTO.getLatitude())
			.longitude(addressDTO.getLongitude())
			.user(user)
			.build();

		user.addAddress(address);
		addressRepository.save(address);
		return "Address added successfully";
	}

	@Transactional
	public String deleteAddress(Long addressId) {
		User user = securityService.getAuthenticatedUser();
		Address address = addressRepository.findById(addressId)
			.orElseThrow(() -> new ResourceNotFoundException("Address not found"));

		if (!address.getUser().getId().equals(user.getId())) {
			throw new UnauthorizedException("Not authorized to delete this address");
		}

		user.removeAddress(address);
		addressRepository.delete(address);
		return "Address deleted successfully";

	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = userRepository.findAll();
		// 보안을 위해 비밀번호는 제거
		users.forEach(user -> user.setPassword(null));
		return users;
	}
}
