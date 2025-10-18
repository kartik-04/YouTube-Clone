package com.youtubeclone.videoService.defaults.rules;

import com.youtubeclone.videoService.defaults.VideoDefaultRule;
import com.youtubeclone.videoService.entities.Video;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Rule to apply default upload date to videos.
 * Sets the upload date to the current date if not specified.
 */
@Component
public class UploadDateDefaultRule implements VideoDefaultRule {
    @Override
    public void apply(Video video) {
        if(video.getUploadDate() == null){
            video.setUploadDate(LocalDate.now());
        }
    }
}
