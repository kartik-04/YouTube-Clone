package com.youtubeclone.video.defaultappliertest;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.defaults.video.rule.TitleDefaultRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TitleDefaultRuleTest {

    private TitleDefaultRule rule;
    private Video video;

    @BeforeEach
    void setUp() {
        rule = new TitleDefaultRule();
        video = new Video();
    }

    @Test
    void apply_shouldSetDefaultTitleWhenNull() {
        video.setTitle(null);

        rule.apply(video);

        assertNotNull(video.getTitle());
        assertTrue(video.getTitle().startsWith("Untitled-"));
    }

    @Test
    void apply_shouldSetDefaultTitleWhenEmpty() {
        video.setTitle("   ");

        rule.apply(video);

        assertNotNull(video.getTitle());
        assertTrue(video.getTitle().startsWith("Untitled-"));
    }

    @Test
    void apply_shouldNotOverrideValidTitle() {
        video.setTitle("My Awesome Video");

        rule.apply(video);

        assertEquals("My Awesome Video", video.getTitle());
    }
}