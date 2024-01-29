package com.github.iamhi.hizone.lite.notes.controllers;

import com.github.iamhi.hizone.lite.notes.controllers.requests.CreateNoteRequest;
import com.github.iamhi.hizone.lite.notes.controllers.requests.UpdateNoteRequest;
import com.github.iamhi.hizone.lite.notes.core.NoteDto;
import com.github.iamhi.hizone.lite.notes.core.NotesService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public record NotesController(
    NotesService notesService
) {

    @GetMapping
    public List<NoteDto> getMyNotes() {
        return notesService.getMyNotes();
    }

    @PostMapping
    public Optional<NoteDto> createNote(@RequestBody CreateNoteRequest request) {
        return notesService.createMyNote(request.content());
    }

    @PutMapping("/{uuid}")
    public Optional<NoteDto> updateNote(@PathVariable String uuid, @RequestBody UpdateNoteRequest request) {
       return notesService.updateMyNote(uuid, request.content());
    }

    @DeleteMapping("/{uuid}")
    public Optional<NoteDto> deleteNote(@PathVariable String uuid) {
        return notesService.deleteMyNote(uuid);
    }
}
