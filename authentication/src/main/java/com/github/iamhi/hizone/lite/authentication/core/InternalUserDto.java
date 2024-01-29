package com.github.iamhi.hizone.lite.authentication.core;

import com.github.iamhi.hizone.lite.authentication.domain.UserDto;

import java.time.Instant;
import java.util.List;

public record InternalUserDto(
    String uuid,
    String username,
    String password,
    String name,
    String email,
    List<String> roles,
    Instant createdAt,
    Instant updatedAt
) {
    public UserDto toUserDto() {
        return new UserDto(
            uuid,
            username,
            name,
            email,
            roles,
            createdAt,
            updatedAt
        );
    }
}
