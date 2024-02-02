package com.github.iamhi.hizone.lite.notes.controllers.requests;

public record UpdateNoteRequest(
    String title,
    String content
) {
}
