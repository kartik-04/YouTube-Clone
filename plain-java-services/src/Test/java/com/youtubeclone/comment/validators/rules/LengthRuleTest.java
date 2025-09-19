package com.youtubeclone.comment.validators.rules;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.exceptions.ValidationException;
import com.youtubeclone.validators.comment.rules.LengthRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LengthRuleTest {

    private final LengthRule rule = new LengthRule(); // assume max length 500

    @Test
    void shouldThrowWhenContentExceedsMax() {
        Comment c = new Comment();
        c.setContent("a".repeat(501));

        assertThrows(ValidationException.class, () -> rule.validate(c));
    }

    @Test
    void shouldPassWhenContentIsWithinLimit() {
        Comment c = new Comment();
        c.setContent("a".repeat(200));

        assertDoesNotThrow(() -> rule.validate(c));
    }

    @Test
    void shouldPassWhenContentIsExactlyAtLimit() {
        Comment c = new Comment();
        c.setContent("a".repeat(500));

        assertDoesNotThrow(() -> rule.validate(c));
    }
}