package com.github.iamhi.hizone.lite.authentication.config;

import com.github.iamhi.hizone.lite.authentication.core.UserService;
import com.github.iamhi.hizone.lite.authentication.domain.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LiteUserDetailsService implements UserDetailsService {

    final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDto> optionalUserDto = userService.findByUsername(username);

        if (optionalUserDto.isPresent()) {
            UserDto userDto = optionalUserDto.get();

            List<SimpleGrantedAuthority> grantedAuthorities = userDto.roles().stream()
                .map(SimpleGrantedAuthority::new).toList();

            return new LiteUser(
                username,
                "",
                grantedAuthorities,
                userDto
            );
        }

        return new User(username, "", Collections.emptyList());
    }

    @Getter
    public static class LiteUser extends User {

        private final UserDto userDto;

        public LiteUser(String username,
                        String password,
                        Collection<? extends GrantedAuthority> authorities,
                        UserDto userDto) {
            super(username, password, authorities);

            this.userDto = userDto;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj)
                && obj instanceof LiteUser liteUser
                && this.userDto.equals(liteUser.userDto);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
