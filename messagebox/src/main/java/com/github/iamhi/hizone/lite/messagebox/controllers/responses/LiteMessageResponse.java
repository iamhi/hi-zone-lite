package com.github.iamhi.hizone.lite.messagebox.controllers.responses;

import java.time.Instant;

public record LiteMessageResponse(
    String uuid,
    String box,
    String content,
    Instant readAt,
    Instant createdAt
) {
}
