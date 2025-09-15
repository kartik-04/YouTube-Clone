package com.youtubeclone.video.defaultappliertest;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.defaults.video.rule.UploadDateDefaultRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UploadDateDefaultRuleTest {

    private UploadDateDefaultRule rule;
    private Video video;

    @BeforeEach
    void setUp() {
        rule = new UploadDateDefaultRule();
        video = new Video();
    }

    @Test
    void apply_shouldSetDefaultUploadDateWhenNull() {
        video.setUploadDate(null);

        rule.apply(video);

        assertNotNull(video.getUploadDate());
        assertEquals(LocalDate.now(), video.getUploadDate());
    }

    @Test
    void apply_shouldNotOverrideValidUploadDate() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        video.setUploadDate(yesterday);

        rule.apply(video);

        assertEquals(yesterday, video.getUploadDate());
    }
}