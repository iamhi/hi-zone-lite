package com.github.iamhi.hizone.lite.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "hizone.lite.authentication")
@Data
public class UserRoleConfig {

    private Map<String, String> roles;
}
