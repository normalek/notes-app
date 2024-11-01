package com.marhaba.notes_app.repository;

import com.marhaba.notes_app.entity.Note;
import com.marhaba.notes_app.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    Page<Note> findByTagsInOrderByCreatedDateDesc(Set<Tag> tags, Pageable pageable);
    Page<Note> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
