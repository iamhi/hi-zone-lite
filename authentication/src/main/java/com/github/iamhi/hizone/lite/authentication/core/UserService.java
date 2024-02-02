package com.github.iamhi.hizone.lite.authentication.core;

import com.github.iamhi.hizone.lite.authentication.domain.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> createUser(
        String username,
        String password,
        String email
    );

    Optional<InternalUserDto> findByUsername(
        String username
    );

    Optional<UserDto> addRole(
        String role,
        String rolePassword
    );

    boolean attemptUserLogin(
        String username,
        String password
    );
}
