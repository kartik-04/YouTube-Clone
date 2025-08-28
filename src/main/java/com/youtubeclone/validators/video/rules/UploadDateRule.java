package com.youtubeclone.validators.video.rules;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.validators.video.ValidationRule;

import java.time.LocalDate;

public class UploadDateRule implements ValidationRule {

    public void validate(Video video) {
            if (video.getUploadDate() == null) {
                throw new IllegalArgumentException("Upload date cannot be null");
            }
            if (video.getUploadDate().isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Upload date cannot be in the future");
            }
            if (video.getUploadDate().isBefore(LocalDate.of(2005, 2, 14))) {
                throw new IllegalArgumentException("Upload date is too old to be valid");
            }
    }
}
