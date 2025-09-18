package com.youtubeclone.comment.validators;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.exceptions.ValidationException;
import com.youtubeclone.validators.comment.CommentValidationPipeline;
import com.youtubeclone.validators.comment.rules.ContentNotEmptyRule;
import com.youtubeclone.validators.comment.rules.LengthRule;
import com.youtubeclone.validators.comment.rules.ProfanityRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentValidatorPipelineTest {

    private CommentValidationPipeline pipeline;

    @BeforeEach
    void setUp() {
        pipeline = new CommentValidationPipeline();
        pipeline.addRules(new ContentNotEmptyRule());
        pipeline.addRules(new LengthRule());
        pipeline.addRules(new ProfanityRule());
    }

    @Test
    void shouldPassForValidComment() {
        Comment c = new Comment();
        c.setContent("This is a safe, valid comment!");

        assertDoesNotThrow(() -> pipeline.validate(c));
    }

    @Test
    void shouldThrowForEmptyContent() {
        Comment c = new Comment();
        c.setContent("");

        assertThrows(ValidationException.class, () -> pipeline.validate(c));
    }

    @Test
    void shouldThrowForTooLongContent() {
        Comment c = new Comment();
        c.setContent("a".repeat(500));

        assertThrows(ValidationException.class, () -> pipeline.validate(c));
    }

    @Test
    void shouldThrowForProfanity() {
        Comment c = new Comment();
        c.setContent("This is damn wrong");

        assertThrows(ValidationException.class, () -> pipeline.validate(c));
    }
}