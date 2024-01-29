package com.github.iamhi.hizone.lite.notes.core;

import java.time.Instant;

public record NoteDto(
    String uuid,
    String content,
    Instant createdAt,
    Instant updatedAt
) {
}
