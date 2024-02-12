package com.github.iamhi.hizone.lite.messagebox.controllers.responses;

import java.time.Instant;

public record MessageResponse(
    String uuid,
    String box,
    String content,
    String createdBy,
    Instant readAt,
    Instant createdAt
) {
}
