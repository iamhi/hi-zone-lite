package com.github.iamhi.hizone.lite.notes.controllers.requests;

public record CreateNoteRequest(
    String title,
    String content
) {
}
