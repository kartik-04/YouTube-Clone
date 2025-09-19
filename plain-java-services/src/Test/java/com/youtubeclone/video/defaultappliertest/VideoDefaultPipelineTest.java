package com.youtubeclone.video.defaultappliertest;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.defaults.video.VideoDefaultPipeline;
import com.youtubeclone.defaults.video.VideoDefaultRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VideoDefaultPipelineTest {
    private VideoDefaultPipeline pipeline;
    private Video video;

    @BeforeEach
    void setUp() {
        pipeline = new VideoDefaultPipeline();
        video = new Video();
    }

    @Test
    void testRulesAreAppliedInSequence() {
        // Arrange
        List<String> executionOrder = new ArrayList<>();

        VideoDefaultRule rule1 = v -> executionOrder.add("rule1");
        VideoDefaultRule rule2 = v -> executionOrder.add("rule2");

        pipeline.addRule(rule1);
        pipeline.addRule(rule2);

        // Act
        pipeline.applyDefault(video);

        // Assert
        assertEquals(2, executionOrder.size(), "Both rules should be applied");
        assertEquals("rule1", executionOrder.get(0), "First rule should be executed first");
        assertEquals("rule2", executionOrder.get(1), "Second rule should be executed second");
    }

    @Test
    void testPipelineDoesNotThrowWhenNoRules() {
        // Act & Assert
        assertDoesNotThrow(() -> pipeline.applyDefault(video));
    }

    @Test
    void testPipelineCanModifyVideoObject() {
        // Arrange
        pipeline.addRule(v -> v.setTitle("Default Title"));

        // Act
        pipeline.applyDefault(video);

        // Assert
        assertEquals("Default Title", video.getTitle(), "Pipeline rule should update video title");
    }
}