package com.github.iamhi.hizone.lite.authentication.controllers.responses;

import java.util.List;

public record UserResponse(
    String username,
    String name,
    String email,
    List<String> roles
) {
}
