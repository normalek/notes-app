package com.marhaba.notes_app.mapper;

import com.marhaba.notes_app.dto.NoteDTO;
import com.marhaba.notes_app.entity.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {
    public Note toEntity(NoteDTO noteDTO) {
        Note note = new Note();
        note.setTitle(noteDTO.getTitle());
        note.setText(noteDTO.getText());
        note.setTags(noteDTO.getTags());
        return note;
    }

    public NoteDTO toDto(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setTitle(note.getTitle());
        dto.setText(note.getText());
        dto.setTags(note.getTags());
        return dto;
    }
}
