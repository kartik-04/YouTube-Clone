package com.youtubeclone.video.defaultappliertest;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Models.video.VideoMetadata;
import com.youtubeclone.defaults.video.VideoDefaultApplier;
import com.youtubeclone.defaults.video.rule.VideoIdDefaultRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VideoDefaultApplierTest {
    private VideoDefaultApplier applier;

    @BeforeEach
    void setUp() {
        applier = new VideoDefaultApplier();
    }

    @Test
    void testApplySetsDefaultsForEmptyVideo() {
        // Arrange
        Video video = new Video();

        // Act
        applier.apply(video);

        // Assert
        assertNotNull(video.getMetadata(), "Metadata should not be null after applying defaults");
        assertNotNull(video.getTitle(), "Title should not be null after applying defaults");
        assertNotNull(video.getUploadDate(), "Upload date should not be null after applying defaults");
        assertNotNull(video.getVideoId(), "VideoId should not be null after applying defaults");
    }

    @Test
    void testApplyDoesNotOverrideExistingValues() {
        // Arrange
            Video video = new Video();
            UUID existingId = UUID.randomUUID();
            video.setVideoId(existingId);

            VideoIdDefaultRule rule = new VideoIdDefaultRule();
            rule.apply(video);

            assertEquals(existingId, video.getVideoId(), "Existing videoId should not be overridden");

    }
}