package com.github.iamhi.hizone.lite.authentication.config;

import com.github.iamhi.hizone.lite.authentication.core.EmptyMemberCacheImpl;
import com.github.iamhi.hizone.lite.authentication.core.MemberCacheImpl;
import com.github.iamhi.hizone.lite.authentication.domain.MemberCache;
import com.github.iamhi.hizone.lite.authentication.domain.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] NOT_AUTHORIZE_PATHS = {
        "/actuator/**",
        "/error",
        "/authentication/user"
//        "/swagger-ui/**"com.example,
//        "/swagger-ui.html",
//        "/webjars/**",
//        "/v3/**"
    };

    private final LiteUserDetailsService liteUserDetailsService;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http
            .securityContext(context -> context.requireExplicitSave(false)) // investigate what this does
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
//            .cors(AbstractHttpConfigurer::disable)
            .cors(customizer -> customizer.configurationSource(request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();

                corsConfiguration.setAllowedOrigins(List.of(
                    "https://www.ibeenhi.com",
                    "https://api.ibeenhi.com",
                    "https://app.ibeenhi.com",
                    "http://127.0.0.1:8082",
                    "http://localhost:8082"));
                corsConfiguration.setAllowedMethods(List.of("*"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedHeaders(List.of(("*")));
                corsConfiguration.setExposedHeaders(List.of("Authorization"));
                corsConfiguration.setMaxAge(3600L);

                return corsConfiguration;
            }))
            .csrf(customizer -> customizer.csrfTokenRequestHandler(requestHandler)
                .ignoringRequestMatchers("/*")
                .csrfTokenRepository(new CookieCsrfTokenRepository())
            )
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(customizer ->
                customizer.requestMatchers(NOT_AUTHORIZE_PATHS)
                    .permitAll()
                    .anyRequest()
                    .fullyAuthenticated()
            )
            .userDetailsService(liteUserDetailsService)
            .oauth2ResourceServer(customizer -> customizer.jwt(Customizer.withDefaults()))
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
        ;

        return http.build();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    MemberCache memberCache() {
        Optional<UserDto> optionalUserDto = getUserDtoFromAuthentication();

        if (optionalUserDto.isPresent()) {
           return new MemberCacheImpl(optionalUserDto.get());
        }

        return new EmptyMemberCacheImpl();
    }

    private Optional<UserDto> getUserDtoFromAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal)
            .map(liteUserPrincipal -> {
                if (liteUserPrincipal instanceof LiteUserDetailsService.LiteUser liteUser) {
                    return liteUser.getUserDto();
                }

                return null;
            });
    }
}
