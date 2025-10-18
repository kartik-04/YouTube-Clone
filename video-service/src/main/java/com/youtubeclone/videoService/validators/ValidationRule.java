package com.youtubeclone.videoService.validators;

import com.youtubeclone.Models.video.Video;

public interface ValidationRule {
    void validate(Video video);
}
