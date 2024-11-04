package com.marhaba.notes_app.mapper;

import com.marhaba.notes_app.dto.NoteDTO;
import com.marhaba.notes_app.dto.NoteSummaryDTO;
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

    public NoteSummaryDTO toDto(Note note) {
        return new NoteSummaryDTO(note.getTitle(), note.getCreatedDate());
    }
}
