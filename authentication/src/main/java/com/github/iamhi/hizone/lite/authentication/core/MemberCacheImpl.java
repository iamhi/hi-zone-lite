package com.github.iamhi.hizone.lite.authentication.core;

import com.github.iamhi.hizone.lite.authentication.domain.MemberCache;
import com.github.iamhi.hizone.lite.authentication.domain.UserDto;
import org.springframework.stereotype.Component;

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
}
