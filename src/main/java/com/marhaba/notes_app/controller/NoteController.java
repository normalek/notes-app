package com.marhaba.notes_app.controller;

import com.marhaba.notes_app.dto.NoteDTO;
import com.marhaba.notes_app.dto.NoteSummaryDTO;
import com.marhaba.notes_app.entity.Note;
import com.marhaba.notes_app.entity.Tag;
import com.marhaba.notes_app.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@Valid @RequestBody NoteDTO noteCreateDTO) {
        Note createdNote = noteService.createNote(noteCreateDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdNote.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable String id, @Valid @RequestBody NoteDTO updatedNoteDTO) {
        Note note = noteService.updateNote(id, updatedNoteDTO);
        return ResponseEntity.ok(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable String id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<NoteSummaryDTO>> listNotes(
            @RequestParam(required = false) Set<Tag> tags,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NoteSummaryDTO> notes = noteService.listNoteSummaries(tags, pageable);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable String id) {
        Note note = noteService.getNoteDetailsById(id);
        return ResponseEntity.ok(note);
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<Map<String, Integer>> getWordStats(@PathVariable String id) {
        Map<String, Integer> statsMap = noteService.getWordStats(id);
        return ResponseEntity.ok(statsMap);
    }
}
