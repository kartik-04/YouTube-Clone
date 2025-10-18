package com.youtubeclone.videoService.validators.rules;

import com.youtubeclone.videoService.entities.Video;
import com.youtubeclone.videoService.entities.VideoMetadata;
import com.youtubeclone.videoService.exceptions.ValidationException;
import com.youtubeclone.videoService.validators.ValidationRule;
import org.springframework.stereotype.Component;

@Component
public class MetadataRule implements ValidationRule {

    public void validate(Video video) {
        VideoMetadata metadata = video.getMetadata();

        if (metadata.getLengthSeconds() <= 0) {
            throw new ValidationException("Video length must be greater than 0");
        }
        if (metadata.getSizeMB() <= 0) {
            throw new ValidationException("Video size must be greater than 0 MB");
        }
        if (metadata.isCaption() && metadata.getLanguage() == null) {
            throw new ValidationException("Caption language must be specified");
        }
        if (metadata.isDownloadable() && metadata.getSizeMB() < 1) {
            throw new ValidationException("Downloadable language must be specified");
        }
        if (metadata.getLanguage() == null || metadata.getLanguage().toString().isBlank()) {
            throw new ValidationException("Language must not be null or empty");
        }
        String lang = metadata.getLanguage().toString().toUpperCase();

        if (!lang.matches("(ENGLISH|HINDI|FRENCH|SPANISH|GERMAN|CHINESE|JAPANESE)")) {
            throw new ValidationException("Language must be inclusive of the options");
        }
    }

}
