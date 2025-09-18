package com.youtubeclone.comment.validators.rules;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.exceptions.ValidationException;
import com.youtubeclone.validators.comment.rules.ProfanityRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfanityRuleTest {

    private final ProfanityRule rule = new ProfanityRule();

    @Test
    void shouldThrowWhenContentHasProfanity() {
        Comment c = new Comment();
        c.setContent("This is damn Fuck!");

        assertThrows(ValidationException.class, () -> rule.validate(c));
    }

    @Test
    void shouldThrowWhenProfanityIsMixedCase() {
        Comment c = new Comment();
        c.setContent("This is DaMn wrong");

        assertThrows(ValidationException.class, () -> rule.validate(c));
    }

    @Test
    void shouldPassWhenNoProfanity() {
        Comment c = new Comment();
        c.setContent("This is clean");

        assertDoesNotThrow(() -> rule.validate(c));
    }

    @Test
    void shouldNotFailForSafeSubstring() {
        Comment c = new Comment();
        c.setContent("adamant is fine");

        assertDoesNotThrow(() -> rule.validate(c));
    }
}