package com.youtubeclone.video.validationTest;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.exceptions.ValidationException;
import com.youtubeclone.validators.video.ValidationPipeline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationPipelineTest {

    private ValidationPipeline pipeline;
    private Video video;

    @BeforeEach
    void setUp() {
        pipeline = new ValidationPipeline();
        video = new Video();
    }

    @Test
    void validate_withNoRules_shouldPass() {
        assertDoesNotThrow(() -> pipeline.validate(video));
    }

    @Test
    void validate_withSinglePassingRule_shouldPass() {
        pipeline.addRule(v -> { /* no-op, always valid */ });

        assertDoesNotThrow(() -> pipeline.validate(video));
    }

    @Test
    void validate_withSingleFailingRule_shouldThrow() {
        pipeline.addRule(v -> {
            throw new ValidationException("Video invalid");
        });

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> pipeline.validate(video)
        );
        assertEquals("Video invalid", ex.getMessage());
    }

    @Test
    void validate_withMultipleRules_oneFails_shouldThrow() {
        pipeline.addRule(v -> { /* valid rule */ });
        pipeline.addRule(v -> { throw new ValidationException("Second rule failed"); });
        pipeline.addRule(v -> { throw new ValidationException("Third rule failed"); });

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> pipeline.validate(video)
        );
        assertEquals("Second rule failed", ex.getMessage());
    }

    @Test
    void validate_withMultiplePassingRules_shouldPass() {
        pipeline.addRule(v -> { /* rule 1 valid */ });
        pipeline.addRule(v -> { /* rule 2 valid */ });

        assertDoesNotThrow(() -> pipeline.validate(video));
    }
}