package com.github.iamhi.hizone.lite.notes.core;

import java.util.List;
import java.util.Optional;

public interface NotesService {

    Optional<NoteDto> createMyNote(String content);

    List<NoteDto> getMyNotes();

    Optional<NoteDto> updateMyNote(String uuid, String content);

    Optional<NoteDto> deleteMyNote(String uuid);
}
