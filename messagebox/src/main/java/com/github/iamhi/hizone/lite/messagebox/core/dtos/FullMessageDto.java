package com.github.iamhi.hizone.lite.messagebox.core.dtos;

import java.time.Instant;

public record FullMessageDto(
    String uuid,
    String box,
    String content,
    String createdBy,
    Instant readAt,
    Instant createdAt
) {
}
