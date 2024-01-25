package com.github.iamhi.hizone.lite.authentication.core;

import com.github.iamhi.hizone.lite.authentication.domain.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> createUser(
        String username,
        String password,
        String email
    );

    Optional<UserDto> findByUsername(
        String username
    );

    boolean attemptUserLogin(
        String username,
        String password
    );
}
