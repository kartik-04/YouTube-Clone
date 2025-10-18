package com.youtubeclone.videoService.validators;


import com.youtubeclone.videoService.entities.Video;

public interface ValidationRule {
    void validate(Video video);
}
