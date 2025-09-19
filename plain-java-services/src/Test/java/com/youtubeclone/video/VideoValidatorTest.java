package com.youtubeclone.video;

import com.youtubeclone.Models.video.Language;
import com.youtubeclone.Models.video.Quality;
import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Models.video.VideoMetadata;
import com.youtubeclone.validators.video.VideoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class VideoValidatorTest {
    private VideoValidator videovalidator;
    private Video  video;

    @BeforeEach
    public void beforeEach() {
        videovalidator = new VideoValidator();
        video = new Video();
    }

    @Test
    @DisplayName("Should call the appropriate Exception class and throw the error")
    void Should_Call_The_Appropriate_Exception() {
        video.setTitle("My Test Video");
        video.setUploadDate(LocalDate.now());

        VideoMetadata metadata = new VideoMetadata();
        metadata.setLengthSeconds(120);
        metadata.setSizeMB(50);
        metadata.setLanguage(Language.ENGLISH);
        metadata.setQuality(Quality.P720);
        video.setMetadata(metadata);
        video.setThumbnailUrl("http://example.com/thumb.jpg");

        assertDoesNotThrow(() -> videovalidator.validate(video));
    }
}
