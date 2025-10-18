package com.youtubeclone.videoService.validators.rules;

import com.youtubeclone.videoService.entities.Video;
import com.youtubeclone.videoService.exceptions.NotFoundException;
import com.youtubeclone.videoService.validators.ValidationRule;
import org.springframework.stereotype.Component;

@Component
public class TitleNotNullRule implements ValidationRule {

    public void validate(Video video) {
        if(video.getTitle() == null || video.getTitle().isEmpty()) {
            throw new NotFoundException("Title cannot be null or empty");
        }
    }
}
