package com.marhaba.notes_app.service;

import com.marhaba.notes_app.dto.NoteDTO;
import com.marhaba.notes_app.dto.NoteSummaryDTO;
import com.marhaba.notes_app.dto.exception.BusinessException;
import com.marhaba.notes_app.entity.Note;
import com.marhaba.notes_app.entity.Tag;
import com.marhaba.notes_app.mapper.NoteMapper;
import com.marhaba.notes_app.repository.NoteRepository;
import com.marhaba.notes_app.service.impl.NoteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NoteMapper noteMapper;

    @InjectMocks
    private NoteServiceImpl noteService;

    @Test
    void testGetNoteById_Found() {
        Note note = new Note();
        note.setId("123");
        note.setTitle("Test Note");

        when(noteRepository.findById("123")).thenReturn(Optional.of(note));

        Note result = noteService.getNoteDetailsById("123");
        assertEquals("Test Note", result.getTitle());
    }

    @Test
    void testGetNoteById_NotFound() {
        when(noteRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> noteService.getNoteDetailsById("123"));
    }

    @Test
    void testCreateNote() {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setTitle("Test Note");
        noteDTO.setText("This is a test note.");

        Note note = new Note();
        note.setTitle("Test Note");
        note.setText("This is a test note.");

        when(noteMapper.toEntity(noteDTO)).thenReturn(note);
        when(noteRepository.save(note)).thenReturn(note);

        Note createdNote = noteService.createNote(noteDTO);

        assertNotNull(createdNote);
        assertEquals("Test Note", createdNote.getTitle());
        assertEquals("This is a test note.", createdNote.getText());
    }

    @Test
    void testUpdateNote() {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setTitle("Updated Note");
        noteDTO.setText("This is an updated note.");

        Note note = new Note();
        note.setId("123");
        note.setTitle("Old Note");
        note.setText("This is an old note.");

        when(noteRepository.findById("123")).thenReturn(Optional.of(note));
        when(noteRepository.save(note)).thenReturn(note);

        Note updatedNote = noteService.updateNote("123", noteDTO);

        assertNotNull(updatedNote);
        assertEquals("Updated Note", updatedNote.getTitle());
        assertEquals("This is an updated note.", updatedNote.getText());
    }

    @Test
    void testDeleteNoteById() {
        Note note = new Note();
        note.setId("123");
        note.setTitle("Note");
        note.setText("This is an note.");
        when(noteRepository.findById("123")).thenReturn(Optional.of(note));
        doNothing().when(noteRepository).deleteById("123");

        noteService.deleteNoteById("123");

        verify(noteRepository, times(1)).deleteById("123");
    }

    void testListNotes_WithTags() {
        Set<Tag> tags = new HashSet<>();
        tags.add(Tag.BUSINESS);
        Pageable pageable = PageRequest.of(0, 10);
        List<Note> notes = Arrays.asList(new Note(), new Note());
        Page<Note> page = new PageImpl<>(notes);

        when(noteRepository.findByTagsInOrderByCreatedDateDesc(tags, pageable)).thenReturn(page);

        Page<NoteSummaryDTO> result = noteService.listNoteSummaries(tags, pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testListNotes_WithoutTags() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Note> notes = Arrays.asList(new Note(), new Note());
        Page<Note> page = new PageImpl<>(notes);

        when(noteRepository.findAllByOrderByCreatedDateDesc(pageable)).thenReturn(page);

        Page<NoteSummaryDTO> result = noteService.listNoteSummaries(Collections.emptySet(), pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testGetWordStats() {
        Note note = new Note();
        note.setId("123");
        note.setText("This is a test note. This note is a test.");

        when(noteRepository.findById("123")).thenReturn(Optional.of(note));

        Map<String, Integer> wordStats = noteService.getWordStats("123");

        assertNotNull(wordStats);
        assertEquals(2, wordStats.get("this"));
        assertEquals(2, wordStats.get("is"));
        assertEquals(2, wordStats.get("a"));
        assertEquals(2, wordStats.get("test"));
        assertEquals(2, wordStats.get("note"));
    }
}
