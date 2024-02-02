package com.github.iamhi.hizone.lite.authentication.controllers.requests;

public record UserAddRoleRequest(
    String role,
    String rolePassword
) {
}
