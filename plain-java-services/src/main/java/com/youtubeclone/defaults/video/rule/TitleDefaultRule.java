package com.youtubeclone.defaults.video.rule;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.defaults.video.VideoDefaultRule;

import java.util.UUID;

public class TitleDefaultRule implements VideoDefaultRule {
    @Override
    public void apply(Video video) {
        if(video.getTitle() == null || video.getTitle().trim().isEmpty()){
            video.setTitle("Untitled-"+ UUID.randomUUID());
        }
    }
}
