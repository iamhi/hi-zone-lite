package com.github.iamhi.hizone.lite.messagebox.core.dtos;

import java.time.Instant;

public record MessageBoxDto(
    String uuid,
    String ownerUuid,
    Instant createdAt
) {
}
