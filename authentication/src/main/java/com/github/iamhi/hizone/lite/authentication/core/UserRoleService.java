package com.github.iamhi.hizone.lite.authentication.core;

public interface UserRoleService {

    boolean validateRole(String role, String rolePassword);
}
