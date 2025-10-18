package com.youtubeclone.videoService.defaults.rules;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.defaults.video.VideoDefaultRule;

import java.util.UUID;

public class VideoIdDefaultRule implements VideoDefaultRule {
    @Override
    public void apply(Video video) {
        if(video.getVideoId() == null){
            video.setVideoId(UUID.randomUUID());
        }
    }
}
