package com.youtubeclone.comment.defaultApplier.rules;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.defaults.comment.rules.TimestampDefaultRule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimestampDefaultRuleTest {

    private final TimestampDefaultRule rule = new TimestampDefaultRule();

    @Test
    void shouldAssignTimestampWhenNull() {
        Comment c = new Comment();
        c.setTimestamp(null);

        rule.apply(c);

        assertNotNull(c.getTimestamp());
        assertEquals(LocalDate.now(), c.getTimestamp());
    }

    @Test
    void shouldNotOverrideExistingTimestamp() {
        Comment c = new Comment();
        c.setTimestamp(LocalDateTime.of(2020, 1, 1, 1, 1));

        rule.apply(c);

        assertEquals(LocalDate.of(2020, 1, 1), c.getTimestamp());
    }
}