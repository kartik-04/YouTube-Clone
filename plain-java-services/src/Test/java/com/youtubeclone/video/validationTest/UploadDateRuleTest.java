package com.youtubeclone.video.validationTest;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.exceptions.NotFoundException;
import com.youtubeclone.exceptions.ValidationException;
import com.youtubeclone.validators.video.rules.UploadDateRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UploadDateRuleTest {

    private UploadDateRule rule;
    private Video video;

    @BeforeEach
    void setUp() {
        rule = new UploadDateRule();
        video = new Video();
    }

    @Test
    void validate_withNullUploadDate_shouldThrowException() {
        video.setUploadDate(null);

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> rule.validate(video)
        );

        assertEquals("Upload date cannot be null", ex.getMessage());
    }

    @Test
    void validate_withFutureUploadDate_shouldThrowException() {
        video.setUploadDate(LocalDate.now().plusDays(5));

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> rule.validate(video)
        );

        assertEquals("Upload date cannot be in the future", ex.getMessage());
    }

    @Test
    void validate_withValidUploadDate_shouldNotThrowException() {
        video.setUploadDate(LocalDate.now().minusDays(1));

        assertDoesNotThrow(() -> rule.validate(video));
    }
}