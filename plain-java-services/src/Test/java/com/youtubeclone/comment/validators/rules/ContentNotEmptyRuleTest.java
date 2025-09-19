package com.youtubeclone.comment.validators.rules;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.exceptions.ValidationException;
import com.youtubeclone.validators.comment.rules.ContentNotEmptyRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentNotEmptyRuleTest {

    private final ContentNotEmptyRule rule = new ContentNotEmptyRule();

    @Test
    void shouldThrowWhenContentIsNull() {
        Comment c = new Comment();
        c.setContent(null);

        assertThrows(ValidationException.class, () -> rule.validate(c));
    }

    @Test
    void shouldThrowWhenContentIsEmpty() {
        Comment c = new Comment();
        c.setContent("");

        assertThrows(ValidationException.class, () -> rule.validate(c));
    }

    @Test
    void shouldThrowWhenContentIsWhitespace() {
        Comment c = new Comment();
        c.setContent("   ");

        assertThrows(ValidationException.class, () -> rule.validate(c));
    }

    @Test
    void shouldPassWhenContentIsValid() {
        Comment c = new Comment();
        c.setContent("This is a valid comment");

        assertDoesNotThrow(() -> rule.validate(c));
    }
}