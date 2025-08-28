package com.youtubeclone.validators.video;

import com.youtubeclone.Models.video.Video;

public interface ValidationRule {
    void validate(Video video);
}
