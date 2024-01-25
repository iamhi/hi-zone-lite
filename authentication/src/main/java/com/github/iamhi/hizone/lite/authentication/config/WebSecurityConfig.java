package com.github.iamhi.hizone.lite.authentication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

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

//    private final UserCacheSupplier userCacheSupplier;

    private String JWT_KEY = "JJWT_KEYJWT_KEYJWT_KEYJWT_KEYJWT_KEYWT_KEY";

    private String JWT_HEADER = "Authorization";

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
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .cors(AbstractHttpConfigurer::disable)
            .cors(customizer -> customizer.configurationSource(request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();

                corsConfiguration.setAllowedOrigins(List.of(
                    "http://127.0.0.1:8088",
                    "http://localhost:8088"));
                corsConfiguration.setAllowedMethods(List.of("*"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedHeaders(List.of(("*")));
                corsConfiguration.setExposedHeaders(List.of("Authorization"));
                corsConfiguration.setMaxAge(3600L);

                return corsConfiguration;
            }))
            .csrf(AbstractHttpConfigurer::disable)
            .csrf(customizer -> customizer.csrfTokenRequestHandler(requestHandler)
                .ignoringRequestMatchers("/")
                .csrfTokenRepository(new CookieCsrfTokenRepository())
            )
            .authorizeHttpRequests(customizer ->
                customizer.requestMatchers(NOT_AUTHORIZE_PATHS)
                    .permitAll()
                    .anyRequest()
                    .fullyAuthenticated()
            )
            .userDetailsService(liteUserDetailsService)
//            .userDetailsService(hubUserDetailsService)
            .oauth2ResourceServer(customizer -> customizer.jwt(Customizer.withDefaults()))
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
        ;

        return http.build();
    }
}
