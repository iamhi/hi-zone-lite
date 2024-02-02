package com.github.iamhi.hizone.lite.authentication.core;

import com.github.iamhi.hizone.lite.authentication.config.UserRoleConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public record UserRoleServiceImpl(
    UserRoleConfig userRoleConfig
) implements UserRoleService {

    @Override
    public boolean validateRole(String role, String rolePassword) {
        return StringUtils.equals(userRoleConfig.getRoles().get(role), rolePassword);
    }
}
