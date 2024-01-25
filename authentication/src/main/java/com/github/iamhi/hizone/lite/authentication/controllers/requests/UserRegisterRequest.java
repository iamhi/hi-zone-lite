package com.github.iamhi.hizone.lite.authentication.controllers.requests;

public record UserRegisterRequest(
    String username,
    String password,
    String email
) {
}
