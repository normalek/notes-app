package com.marhaba.notes_app.entity;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "notes")
public class Note {
    @Id
    private String id;

    @NotEmpty(message = "Title is required")
    private String title;

    @NotEmpty(message = "Text is required")
    private String text;

    private LocalDateTime createdDate;

    private Set<Tag> tags;

    public Note() {
        this.createdDate = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotEmpty(message = "Title is required") String getTitle() {
        return title;
    }

    public void setTitle(@NotEmpty(message = "Title is required") String title) {
        this.title = title;
    }

    public @NotEmpty(message = "Text is required") String getText() {
        return text;
    }

    public void setText(@NotEmpty(message = "Text is required") String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
