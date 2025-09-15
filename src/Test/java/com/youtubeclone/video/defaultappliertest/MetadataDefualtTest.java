package com.youtubeclone.video.defaultappliertest;

import com.youtubeclone.Models.video.Quality;
import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Models.video.VideoMetadata;
import com.youtubeclone.defaults.video.rule.MetadataDefaultRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetadataDefaultRuleTest {

    private MetadataDefaultRule rule;
    private Video video;

    @BeforeEach
    void setUp() {
        rule = new MetadataDefaultRule();
        video = new Video();
        video.setMetadata(new VideoMetadata()); // ensure metadata object is present
    }

    @Test
    void apply_shouldSetDefaultsWhenFieldsAreInvalid() {
        VideoMetadata metadata = video.getMetadata();
        metadata.setSizeMB(0.0);
        metadata.setLengthSeconds(0);
        metadata.setCaption(false);
        metadata.setDownloadable(false);
        metadata.setQuality(null);

        rule.apply(video);

        assertEquals(0.0, metadata.getSizeMB());
        assertEquals(0, metadata.getLengthSeconds());
        assertTrue(metadata.isCaption());
        assertTrue(metadata.isDownloadable());
        assertEquals(Quality.P360, metadata.getQuality());
    }

    @Test
    void apply_shouldNotOverrideValidFields() {
        VideoMetadata metadata = video.getMetadata();
        metadata.setSizeMB(100.5);
        metadata.setLengthSeconds(120);
        metadata.setCaption(true);
        metadata.setDownloadable(true);
        metadata.setQuality(Quality.P720);

        rule.apply(video);

        assertEquals(100.5, metadata.getSizeMB());
        assertEquals(120, metadata.getLengthSeconds());
        assertTrue(metadata.isCaption());
        assertTrue(metadata.isDownloadable());
        assertEquals(Quality.P720, metadata.getQuality());
    }
}