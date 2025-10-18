package com.youtubeclone.videoService.defaults.rules;

import com.youtubeclone.videoService.defaults.VideoDefaultRule;
import com.youtubeclone.videoService.entities.Video;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Rule to apply default video ID.
 * Generates a random UUID if video ID is not set.
 */
@Component
public class VideoIdDefaultRule implements VideoDefaultRule {
    @Override
    public void apply(Video video) {
        if(video.getVideoId() == null){
            video.setVideoId(UUID.randomUUID());
        }
    }
}
