package com.youtubeclone.videoService.validators.rules;

import com.youtubeclone.videoService.entities.Video;
import com.youtubeclone.videoService.exceptions.NotFoundException;
import com.youtubeclone.videoService.exceptions.ValidationException;
import com.youtubeclone.videoService.validators.ValidationRule;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
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
