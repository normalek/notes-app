package com.marhaba.notes_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marhaba.notes_app.dto.NoteDTO;
import com.marhaba.notes_app.dto.NoteSummaryDTO;
import com.marhaba.notes_app.entity.Note;
import com.marhaba.notes_app.entity.Tag;
import com.marhaba.notes_app.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
@ExtendWith(MockitoExtension.class)
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Note note;
    private NoteDTO noteDTO;
    private NoteSummaryDTO noteSummaryDTO;

    @BeforeEach
    void setUp() {
        note = new Note();
        note.setId("123");
        note.setTitle("Test Note");
        note.setText("This is a test note.");
        note.setTags(Set.of(Tag.IMPORTANT));

        noteDTO = new NoteDTO();
        noteDTO.setTitle("Test Note");
        noteDTO.setText("This is a test note.");
        noteDTO.setTags(Set.of(Tag.IMPORTANT));

        noteSummaryDTO = new NoteSummaryDTO("Test Note", LocalDateTime.now());
    }

    @Test
    void testCreateNote() throws Exception {
        when(noteService.createNote(any(NoteDTO.class))).thenReturn(note);

        mockMvc.perform(post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(note.getId()))
                .andExpect(jsonPath("$.title").value(note.getTitle()));
    }

    @Test
    void testUpdateNote() throws Exception {
        when(noteService.updateNote(eq("123"), any(NoteDTO.class))).thenReturn(note);

        mockMvc.perform(put("/api/v1/notes/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(note.getId()))
                .andExpect(jsonPath("$.title").value(note.getTitle()));
    }

    @Test
    void testDeleteNoteById() throws Exception {
        mockMvc.perform(delete("/api/v1/notes/123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testListNotes() throws Exception {
        when(noteService.listNoteSummaries(anySet(), any())).thenReturn(new PageImpl<>(Collections.singletonList(noteSummaryDTO)));

        mockMvc.perform(get("/api/v1/notes")
                        .param("page", "0")
                        .param("tags", "BUSINESS")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value(note.getTitle()));
    }

    @Test
    void testGetNoteById() throws Exception {
        when(noteService.getNoteDetailsById("123")).thenReturn(note);

        mockMvc.perform(get("/api/v1/notes/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(note.getId()))
                .andExpect(jsonPath("$.title").value(note.getTitle()));
    }

    @Test
    void testGetWordStats() throws Exception {
        Map<String, Integer> wordStats = Map.of("this", 2, "is", 2, "a", 2, "test", 2, "note", 2);
        when(noteService.getWordStats("123")).thenReturn(wordStats);

        mockMvc.perform(get("/api/v1/notes/123/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.this").value(2))
                .andExpect(jsonPath("$.is").value(2))
                .andExpect(jsonPath("$.a").value(2))
                .andExpect(jsonPath("$.test").value(2))
                .andExpect(jsonPath("$.note").value(2));
    }
}