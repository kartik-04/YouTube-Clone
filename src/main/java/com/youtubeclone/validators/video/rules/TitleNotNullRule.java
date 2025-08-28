package com.youtubeclone.validators.video.rules;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.validators.video.ValidationRule;

public class TitleNotNullRule implements ValidationRule {

    public void validate(Video video) {
        if(video.getTitle() != null && !video.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
    }
}
