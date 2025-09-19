package com.youtubeclone.comment.defaultApplier;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.Models.comment.CommentStatus;
import com.youtubeclone.defaults.comment.CommentDefaultApplier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentDefaultApplierTest {

    private final CommentDefaultApplier applier = new CommentDefaultApplier();

    @Test
    void shouldApplyAllDefaultsWhenFieldsMissing() {
        Comment c = new Comment();

        applier.apply(c);

        assertNotNull(c.getCommentId());
        assertNotNull(c.getTimestamp());
        assertEquals(CommentStatus.ACTIVE, c.getStatus());
    }

    @Test
    void shouldNotOverrideNonNullFields() {
        Comment c = new Comment();
        c.setStatus(CommentStatus.FLAGGED);

        applier.apply(c);

        assertEquals(CommentStatus.FLAGGED, c.getStatus());
    }
}