package com.youtubeclone.comment.defaultApplier.rules;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.defaults.comment.rules.CommentIdDefaultRule;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CommentIdDefaultRuleTest {

    private final CommentIdDefaultRule rule = new CommentIdDefaultRule();

    @Test
    void shouldAssignIdWhenNull() {
        Comment c = new Comment();
        c.setCommentId(null);

        rule.apply(c);

        assertNotNull(c.getCommentId());
    }

    @Test
    void shouldNotOverrideExistingId() {
        UUID id = UUID.randomUUID();
        Comment c = new Comment();
        c.setCommentId(id);

        rule.apply(c);

        assertEquals(id, c.getCommentId());
    }
}