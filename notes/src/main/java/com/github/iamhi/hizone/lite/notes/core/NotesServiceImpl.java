package com.github.iamhi.hizone.lite.notes.core;

import com.github.iamhi.hizone.lite.authentication.domain.MemberCache;
import com.github.iamhi.hizone.lite.notes.database.NoteEntity;
import com.github.iamhi.hizone.lite.notes.database.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public record NotesServiceImpl(
    MemberCache memberCache,
    NoteRepository noteRepository
) implements NotesService {

    static NoteDto mapEntityToDto(NoteEntity noteEntity) {
        return new NoteDto(
            noteEntity.getUuid(),
            noteEntity.getContent(),
            noteEntity.getCreatedAt(),
            noteEntity.getUpdatedAt()
        );
    }

    @Override
    public Optional<NoteDto> createMyNote(String content) {
        NoteEntity noteEntity = generateEmptyEntity();

        noteEntity.setOwnerUuid(memberCache.getUserUuid());
        noteEntity.setContent(content);

        return Optional.of(mapEntityToDto(noteRepository.save(noteEntity)));
    }

    @Override
    public List<NoteDto> getMyNotes() {
        return noteRepository.findByOwnerUuid(memberCache.getUserUuid())
            .stream()
            .map(NotesServiceImpl::mapEntityToDto)
            .toList();
    }

    @Override
    public Optional<NoteDto> updateMyNote(String uuid, String content) {
        Optional<NoteEntity> optionalNoteEntity = noteRepository.findByUuid(uuid);

        if (optionalNoteEntity.isPresent()) {
            NoteEntity noteEntity = optionalNoteEntity.get();

            noteEntity.setContent(content);
            noteEntity.setUpdatedAt(Instant.now());

            return Optional.of(NotesServiceImpl.mapEntityToDto(noteRepository.save(noteEntity)));
        }

        return Optional.empty();
    }

    @Override
    public Optional<NoteDto> deleteMyNote(String uuid) {
        Optional<NoteEntity> optionalNoteEntity = noteRepository.findByUuid(uuid);

        if (optionalNoteEntity.isPresent()) {
            NoteEntity noteEntity = optionalNoteEntity.get();
            NoteDto noteDto = NotesServiceImpl.mapEntityToDto(noteEntity);

            noteRepository.delete(noteEntity);

            return Optional.of(noteDto);
        }
        return Optional.empty();
    }

    private NoteEntity generateEmptyEntity() {
        NoteEntity noteEntity = new NoteEntity();

        noteEntity.setUuid(UUID.randomUUID().toString());
        noteEntity.setUpdatedAt(Instant.now());
        noteEntity.setCreatedAt(Instant.now());

        return noteEntity;
    }
}
