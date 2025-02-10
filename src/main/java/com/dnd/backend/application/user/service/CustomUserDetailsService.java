package com.dnd.backend.application.user.service;

import com.dnd.backend.application.user.dto.CustomUserDetails;
import com.dnd.backend.domain.User;
import com.dnd.backend.infrastructure.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //DB에서 조회
        User findByEmail = userRepository.findByEmail(username);

        if (findByEmail != null) {
            return new CustomUserDetails(findByEmail);
        }
        return null;
    }
}