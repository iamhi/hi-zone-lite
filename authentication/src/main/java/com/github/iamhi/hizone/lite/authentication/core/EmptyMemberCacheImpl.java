package com.github.iamhi.hizone.lite.authentication.core;

import com.github.iamhi.hizone.lite.authentication.domain.InvalidRoleException;
import com.github.iamhi.hizone.lite.authentication.domain.MemberCache;
import com.github.iamhi.hizone.lite.authentication.domain.UserDto;
import org.apache.commons.lang3.StringUtils;

public record EmptyMemberCacheImpl() implements MemberCache {

    @Override
    public UserDto getUser() {
        return null;
    }

    @Override
    public String getUserUuid() {
        return StringUtils.EMPTY;
    }

    @Override
    public void validateRole(String role) throws InvalidRoleException {
        throw new InvalidRoleException(role);
    }
}
