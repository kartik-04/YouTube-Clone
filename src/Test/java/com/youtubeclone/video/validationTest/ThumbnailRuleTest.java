package com.youtubeclone.video.validationTest;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.exceptions.ValidationException;
import com.youtubeclone.validators.video.rules.ThumbnailRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThumbnailRuleTest {

    private ThumbnailRule rule;
    private Video video;

    @BeforeEach
    void setUp() {
        rule = new ThumbnailRule();
        video = new Video();
    }

    @Test
    void validate_withNullThumbnail_shouldThrowException() {
        video.setThumbnail(null);

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> rule.validate(video)
        );

        assertEquals("Thumbnail cannot be null or empty", ex.getMessage());
    }

    @Test
    void validate_withValidThumbnail_shouldNotThrowException() {
        video.setThumbnail("https://example.com/thumb.jpg");

        assertDoesNotThrow(() -> rule.validate(video));
    }
}