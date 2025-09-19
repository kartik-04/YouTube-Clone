package com.youtubeclone.comment.defaultApplier;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.Models.comment.CommentStatus;
import com.youtubeclone.defaults.comment.CommentDefaultPipeline;
import com.youtubeclone.defaults.comment.rules.CommentIdDefaultRule;
import com.youtubeclone.defaults.comment.rules.StatusDefaultRule;
import com.youtubeclone.defaults.comment.rules.TimestampDefaultRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentDefaultPipelineTest {

    private CommentDefaultPipeline pipeline;

    @BeforeEach
    void setUp() {
        pipeline = new CommentDefaultPipeline();
        pipeline.addRules(new CommentIdDefaultRule());
        pipeline.addRules(new TimestampDefaultRule());
        pipeline.addRules(new StatusDefaultRule());
    }

    @Test
    void shouldApplyAllRules() {
        Comment c = new Comment();

        pipeline.applyDefaults(c);

        assertNotNull(c.getCommentId());
        assertNotNull(c.getTimestamp());
        assertEquals(CommentStatus.ACTIVE, c.getStatus());
    }

    @Test
    void shouldNotOverrideExistingValues() {
        Comment c = new Comment();
        c.setStatus(CommentStatus.FLAGGED);

        pipeline.applyDefaults(c);

        assertEquals(CommentStatus.FLAGGED, c.getStatus());
    }
}