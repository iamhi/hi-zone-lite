package com.github.iamhi.hizone.lite.messagebox.core.dtos;

import java.time.Instant;

public record LiteMessageDto(
    String uuid,
    String box,
    String content,
    Instant readAt,
    Instant createdAt
) {
}
