package com.dnd.backend.user.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.dnd.backend.user.entity.MemberEntity;

import lombok.Getter;

@Getter
public class CustomeUserDetails implements UserDetails, OAuth2User {
	private Long id;
	private String name;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	public CustomeUserDetails(Long id, String name, String email, String password,
		Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static CustomeUserDetails create(MemberEntity memberEntity) {
		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
		return new CustomeUserDetails(
			memberEntity.getId(),
			memberEntity.getName(),
			memberEntity.getEmail(),
			memberEntity.getPassword(),
			authorities
		);
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	// OAuth2User 구현
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "CustomeUserDetails{" +
			"id=" + id +
			", email='" + email + '\'' +
			", password='" + password + '\'' +
			", authorities=" + authorities +
			", attributes=" + attributes +
			'}';
	}
}
