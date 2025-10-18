package com.youtubeclone.videoService.defaults.rules;
import com.youtubeclone.videoService.defaults.VideoDefaultRule;
import com.youtubeclone.videoService.entities.Video;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TitleDefaultRule implements VideoDefaultRule {
    @Override
    public void apply(Video video) {
        if(video.getTitle() == null || video.getTitle().trim().isEmpty()){
            video.setTitle("Untitled-"+ UUID.randomUUID());
        }
    }
}
