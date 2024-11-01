package com.marhaba.notes_app.service.impl;

import com.marhaba.notes_app.dto.NoteDTO;
import com.marhaba.notes_app.dto.error.ErrorCode;
import com.marhaba.notes_app.dto.exception.BusinessException;
import com.marhaba.notes_app.entity.Note;
import com.marhaba.notes_app.entity.Tag;
import com.marhaba.notes_app.mapper.NoteMapper;
import com.marhaba.notes_app.repository.NoteRepository;
import com.marhaba.notes_app.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public NoteServiceImpl(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    public Note createNote(NoteDTO noteCreateDTO) {
        Note note = noteMapper.toEntity(noteCreateDTO);
        return noteRepository.save(note);
    }

    public Note updateNote(String id, NoteDTO updatedNoteDTO) {
        Note note = getNoteById(id);
        note.setTitle(updatedNoteDTO.getTitle());
        note.setText(updatedNoteDTO.getText());
        note.setTags(updatedNoteDTO.getTags());
        return noteRepository.save(note);
    }

    public void deleteNoteById(String id) {
        getNoteById(id);
        noteRepository.deleteById(id);
    }

    public Note getNoteById(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Note with id {} not found in the system", id);
                    return new BusinessException(ErrorCode.NOTE_NOT_FOUND, HttpStatus.NOT_FOUND);
                });
    }

    public Page<Note> listNotes(Set<Tag> tags, Pageable pageable) {
        if (CollectionUtils.isEmpty(tags)) {
            return noteRepository.findAllByOrderByCreatedDateDesc(pageable);
        }
        return noteRepository.findByTagsInOrderByCreatedDateDesc(tags, pageable);
    }

    public Map<String, Integer> getWordStats(String noteId) {
        Note note = getNoteById(noteId);
        return Stream.of(note.getText().toLowerCase().split("\\W+"))
                .map(String::toLowerCase)
                .collect(Collectors.toMap(
                        word -> word,
                        word -> 1,
                        Integer::sum
                ));
    }
}
