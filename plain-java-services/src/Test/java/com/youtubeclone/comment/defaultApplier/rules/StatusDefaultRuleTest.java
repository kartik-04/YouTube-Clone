package com.youtubeclone.comment.defaultApplier.rules;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.Models.comment.CommentStatus;
import com.youtubeclone.defaults.comment.rules.StatusDefaultRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusDefaultRuleTest {

    private final StatusDefaultRule rule = new StatusDefaultRule();

    @Test
    void shouldDefaultToActiveWhenNull() {
        Comment c = new Comment();
        c.setStatus(null);

        rule.apply(c);

        assertEquals(CommentStatus.ACTIVE, c.getStatus());
    }

    @Test
    void shouldNotOverrideExistingStatus() {
        Comment c = new Comment();
        c.setStatus(CommentStatus.FLAGGED);

        rule.apply(c);

        assertEquals(CommentStatus.FLAGGED, c.getStatus());
    }
}