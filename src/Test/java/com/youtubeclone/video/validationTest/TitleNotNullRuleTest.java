package com.youtubeclone.video.validationTest;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.exceptions.NotFoundException;
import com.youtubeclone.validators.video.rules.TitleNotNullRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TitleNotNullRuleTest {

    private TitleNotNullRule rule;
    private Video video;

    @BeforeEach
    void setUp() {
        rule = new TitleNotNullRule();
        video = new Video(); // default constructor sets title = ""
    }

    @Test
    void validate_withNullTitle_shouldThrowException() {
        video.setTitle(null);

        Exception ex = assertThrows(NotFoundException.class, () -> rule.validate(video));
        assertEquals("Title cannot be null or empty", ex.getMessage());

    }

    @Test
    void validate_withEmptyTitle_shouldThrowException() {
        video.setTitle("");

        Exception ex = assertThrows(NotFoundException.class, () -> rule.validate(video));
        assertEquals("Title cannot be null or empty", ex.getMessage());
    }

    @Test
    void validate_withValidTitle_shouldNotThrowException() {
        video.setTitle("My Awesome Video");

        assertDoesNotThrow(() -> rule.validate(video));
    }
}