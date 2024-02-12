package com.github.iamhi.hizone.lite.authentication.domain;

public interface MemberCache {

    String SERVICE_ROLE = "SERVICE_ROLE";

    UserDto getUser();

    String getUserUuid();

    void validateRole(String role) throws InvalidRoleException;
}
