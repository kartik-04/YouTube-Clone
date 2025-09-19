package com.youtubeclone.video.defaultappliertest;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.defaults.video.rule.VideoIdDefaultRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VideoIdDefaultRuleTest {

    private VideoIdDefaultRule rule;
    private Video video;

    @BeforeEach
    void setUp() {
        rule = new VideoIdDefaultRule();
        video = new Video();
    }

    @Test
    void apply_shouldSetDefaultVideoIdWhenNull() {
        video.setVideoId(null);

        rule.apply(video);

        assertNotNull(video.getVideoId());
    }

    @Test
    void apply_shouldNotOverrideValidVideoId() {
        UUID id = UUID.randomUUID();
        video.setVideoId(id);

        rule.apply(video);

        assertEquals(id, video.getVideoId());
    }
}