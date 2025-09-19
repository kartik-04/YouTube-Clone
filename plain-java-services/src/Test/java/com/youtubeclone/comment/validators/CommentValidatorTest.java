package com.youtubeclone.comment.validators;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.validators.comment.CommentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentValidatorTest {

    private CommentValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CommentValidator();
    }

    @Test
    void shouldValidateValidComment() {
        Comment c = new Comment();
        c.setContent("Nice video!");

        assertDoesNotThrow(() -> validator.validate(c));
    }

    @Test
    void shouldThrowForInvalidComment() {
        Comment c = new Comment();
        c.setContent(""); // empty content

        assertThrows(RuntimeException.class, () -> validator.validate(c));
    }
}