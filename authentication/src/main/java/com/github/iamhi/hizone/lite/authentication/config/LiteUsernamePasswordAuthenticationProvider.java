package com.github.iamhi.hizone.lite.authentication.config;

import com.github.iamhi.hizone.lite.authentication.core.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public record LiteUsernamePasswordAuthenticationProvider (
    UserService userService
) implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (userService.attemptUserLogin(username, password)) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
