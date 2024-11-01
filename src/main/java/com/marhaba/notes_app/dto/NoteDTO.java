package com.marhaba.notes_app.dto;

import com.marhaba.notes_app.entity.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class NoteDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Text is required")
    private String text;

    private Set<Tag> tags;
}
