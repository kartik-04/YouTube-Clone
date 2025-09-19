package com.youtubeclone.validators.video.rules;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.exceptions.NotFoundException;
import com.youtubeclone.exceptions.ValidationException;
import com.youtubeclone.validators.video.ValidationRule;

import java.time.LocalDate;

public class UploadDateRule implements ValidationRule {

    public void validate(Video video) {
            if (video.getUploadDate() == null) {
                throw new NotFoundException("Upload date cannot be null");
            }
            if (video.getUploadDate().isAfter(LocalDate.now())) {
                throw new ValidationException("Upload date cannot be in the future");
            }
            if (video.getUploadDate().isBefore(LocalDate.of(2005, 2, 14))) {
                throw new ValidationException("Upload date is too old to be valid");
            }
    }
}
