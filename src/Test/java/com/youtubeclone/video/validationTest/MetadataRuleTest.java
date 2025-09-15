package com.youtubeclone.video.validationTest;

import com.youtubeclone.Models.video.Language;
import com.youtubeclone.Models.video.Quality;
import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Models.video.VideoMetadata;
import com.youtubeclone.exceptions.ValidationException;
import com.youtubeclone.validators.video.rules.MetadataRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetadataRuleTest {

    private MetadataRule rule;
    private Video video;

    @BeforeEach
    void setUp() {
        rule = new MetadataRule();
        video = new Video();
    }

    @Test
    void validate_withNullMetadata_shouldThrowException() {
        video.setMetadata(null);

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> rule.validate(video)
        );

        assertEquals("Video metadata cannot be null", ex.getMessage());
    }

    @Test
    void validate_withIncompleteMetadata_shouldThrowException() {
        VideoMetadata metadata = new VideoMetadata();
        metadata.setLanguage(null); // missing important field
        video.setMetadata(metadata);

        assertThrows(ValidationException.class, () -> rule.validate(video));
    }

    @Test
    void validate_withValidMetadata_shouldNotThrowException() {
        VideoMetadata metadata = new VideoMetadata();
        metadata.setLanguage(Language.ENGLISH);
        metadata.setQuality(Quality.P360);
        metadata.setLengthSeconds(21);
        metadata.setSizeMB(10.2);
        video.setMetadata(metadata);

        assertDoesNotThrow(() -> rule.validate(video));
    }
}