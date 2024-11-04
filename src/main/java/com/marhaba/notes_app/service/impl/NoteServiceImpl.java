package com.marhaba.notes_app.service.impl;

import com.marhaba.notes_app.dto.NoteDTO;
import com.marhaba.notes_app.dto.NoteSummaryDTO;
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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        Note note = getNoteDetailsById(id);
        note.setTitle(updatedNoteDTO.getTitle());
        note.setText(updatedNoteDTO.getText());
        note.setTags(updatedNoteDTO.getTags());
        return noteRepository.save(note);
    }

    public void deleteNoteById(String id) {
        getNoteDetailsById(id);
        noteRepository.deleteById(id);
    }

    public Page<NoteSummaryDTO> listNoteSummaries(Set<Tag> tags, Pageable pageable) {
        Page<Note> notesPage;

        if (tags == null || tags.isEmpty()) {
            notesPage = noteRepository.findAllByOrderByCreatedDateDesc(pageable);
        } else {
            notesPage = noteRepository.findByTagsInOrderByCreatedDateDesc(tags, pageable);
        }

        return notesPage.map(noteMapper::toDto);

    }

    public Note getNoteDetailsById(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Note with id {} not found in the system", id);
                    return new BusinessException(ErrorCode.NOTE_NOT_FOUND, HttpStatus.NOT_FOUND);
                });
    }

    public Map<String, Integer> getWordStats(String noteId) {
        Note note = getNoteDetailsById(noteId);
        return Arrays.stream(note.getText().toLowerCase().split("[^a-zA-Z]+"))
                .collect(Collectors.groupingBy(word -> word, LinkedHashMap::new, Collectors.summingInt(word -> 1)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
