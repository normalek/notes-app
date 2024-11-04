package com.marhaba.notes_app.service;

import com.marhaba.notes_app.dto.NoteDTO;
import com.marhaba.notes_app.dto.NoteSummaryDTO;
import com.marhaba.notes_app.entity.Note;
import com.marhaba.notes_app.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Set;

public interface NoteService {
    Note createNote(NoteDTO noteCreateDTO);

    Note updateNote(String id, NoteDTO updatedNote);

    void deleteNoteById(String id);

    Page<NoteSummaryDTO> listNoteSummaries(Set<Tag> tags, Pageable pageable);

    public Note getNoteDetailsById(String id);

    Map<String, Integer> getWordStats(String id);
}
