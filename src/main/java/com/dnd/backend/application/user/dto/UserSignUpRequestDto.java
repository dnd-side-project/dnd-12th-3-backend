package com.dnd.backend.application.user.dto;


import com.dnd.backend.domain.User;
import com.dnd.backend.domain.loginEnum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import lombok.*;


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    public class UserSignUpRequestDto {

        private String email;
        private String password;
        private String checkPassword;
        private String name;
        private Role role;


        @Builder
        public User toEntity() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .role(role)
                    .build();
        }
    }