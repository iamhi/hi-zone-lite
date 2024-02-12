package com.github.iamhi.hizone.lite.authentication.domain;

public class InvalidRoleException extends RuntimeException {

    public InvalidRoleException(String role) {
        super("Invalid role: " + role);
    }
}
