package com.github.iamhi.hizone.lite.authentication.controllers;

import com.github.iamhi.hizone.lite.authentication.controllers.requests.UserRegisterRequest;
import com.github.iamhi.hizone.lite.authentication.controllers.responses.UserResponse;
import com.github.iamhi.hizone.lite.authentication.core.UserService;
import com.github.iamhi.hizone.lite.authentication.domain.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/authentication/user")
public record UserController(
    UserService userService
) {

    @PostMapping
    public Optional<UserResponse> register(@RequestBody UserRegisterRequest registerRequest) {
        return userService.createUser(
            registerRequest.username(),
            registerRequest.password(),
            registerRequest.email()
        ).map(this::generateResponse);
    }

    private UserResponse generateResponse(UserDto userDto) {
        return new UserResponse(
            userDto.username(),
            userDto.name(),
            userDto.email(),
            userDto.roles()
        );
    }
}
