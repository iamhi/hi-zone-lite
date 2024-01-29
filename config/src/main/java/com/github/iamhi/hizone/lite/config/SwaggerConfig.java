package com.github.iamhi.hizone.lite.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Hi-Zone Lite api", version = "v1"),
    servers = {
        @Server(
            url = "http://127.0.0.1:8082/hi-zone-api/lite",
            description = "Local variant"
        ),
        @Server(
            url = "https://api.ibeenhi.com/hi-zone-api/lite",
            description = "IBeenHi Api variant"
        )
    })
public class SwaggerConfig {
    public static final String SECURITY_SCHEME_NAME = "lite";
}
