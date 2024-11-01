package com.marhaba.notes_app.mapper;

import com.marhaba.notes_app.dto.NoteDTO;
import com.marhaba.notes_app.entity.Note;
import com.marhaba.notes_app.entity.Tag;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class NoteMapperTest {

    private final NoteMapper noteMapper = new NoteMapper();

    @Test
    void testToEntity() {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setTitle("Test Note");
        noteDTO.setText("This is a test note.");
        noteDTO.setTags(Set.of(Tag.IMPORTANT));

        Note note = noteMapper.toEntity(noteDTO);

        assertNotNull(note);
        assertEquals("Test Note", note.getTitle());
        assertEquals("This is a test note.", note.getText());
        assertEquals(1, note.getTags().size());
        assertTrue(note.getTags().contains(Tag.IMPORTANT));
    }

    @Test
    void testToDto() {
        Note note = new Note();
        note.setTitle("Test Note");
        note.setText("This is a test note.");
        note.setTags(Set.of(Tag.IMPORTANT));

        NoteDTO noteDTO = noteMapper.toDto(note);

        assertNotNull(noteDTO);
        assertEquals("Test Note", noteDTO.getTitle());
        assertEquals("This is a test note.", noteDTO.getText());
        assertEquals(1, noteDTO.getTags().size());
        assertTrue(noteDTO.getTags().contains(Tag.IMPORTANT));
    }
}