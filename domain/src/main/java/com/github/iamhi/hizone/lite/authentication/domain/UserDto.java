package com.github.iamhi.hizone.lite.authentication.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public record UserDto(
    String uuid,
    String username,
    String name,
    String email,
    List<String> roles,
    Instant createdAt,
    Instant updatedAt
) implements Serializable {
}
