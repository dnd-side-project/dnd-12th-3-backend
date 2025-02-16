package com.dnd.backend.user.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String name;

	// 일반 로그인 시 암호, 소셜 로그인 시엔 빈 문자열 또는 null 사용
	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SocialType socialType = SocialType.LOCAL;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> addresses = new ArrayList<>();

	// 모든 필드를 포함한 생성자 (빌더 패턴을 위해 필요)
	@Builder
	public User(Long id, String name, String email, String password, SocialType socialType, List<Address> addresses) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.socialType = socialType;
		this.addresses = addresses != null ? addresses : new ArrayList<>();
	}

	public void addAddress(Address address) {
		if (this.addresses.size() >= 2) {
			throw new IllegalStateException("Cannot add more than 2 addresses");
		}
		this.addresses.add(address);
		address.setUser(this);
	}

	public void removeAddress(Address address) {
		this.addresses.remove(address);
		address.setUser(null);
	}
}
