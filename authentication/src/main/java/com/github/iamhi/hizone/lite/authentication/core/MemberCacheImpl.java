package com.github.iamhi.hizone.lite.authentication.core;

import com.github.iamhi.hizone.lite.authentication.domain.InvalidRoleException;
import com.github.iamhi.hizone.lite.authentication.domain.MemberCache;
import com.github.iamhi.hizone.lite.authentication.domain.UserDto;

public record MemberCacheImpl(
    UserDto userDto
) implements MemberCache {

    @Override
    public UserDto getUser() {
        return userDto;
    }

    @Override
    public String getUserUuid() {
        return userDto.uuid();
    }

    @Override
    public void validateRole(String role) throws InvalidRoleException {
        if (!userDto.roles().contains(role)) {
           throw new InvalidRoleException(role);
        }
    }
}
