package com.dnd.backend.domain.loginEnum;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ROLE_PARTICIPANT, ROLE_ORGANIZER, ROLE_ADMIN;

    @JsonCreator
    public static Role fromString(String roleStr) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(roleStr)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No constant with text " + roleStr + " found");
    }
}