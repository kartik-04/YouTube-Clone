package com.youtubeclone.defaults.video;

import com.youtubeclone.Models.video.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoDefaultPipeline {
    List<VideoDefaultRule> rules = new ArrayList<>();

    public void addRule(VideoDefaultRule rule) {
        rules.add(rule);
    }

    public void applyDefault(Video video) {
        for(VideoDefaultRule rule : rules){
            rule.apply(video);
        }
    }
}
