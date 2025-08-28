package com.youtubeclone.validators.video.rules;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Models.video.VideoMetadata;
import com.youtubeclone.validators.video.ValidationRule;

public class MetadataRule implements ValidationRule {

    public void validate(Video video) {
        VideoMetadata metadata = video.getMetadata();

        if (metadata.getLengthSeconds() <= 0) {
            throw new IllegalArgumentException("Video length must be greater than 0");
        }
        if (metadata.getSizeMB() <= 0) {
            throw new IllegalArgumentException("Video size must be greater than 0 MB");
        }
        if (metadata.isCaption() && metadata.getLanguage() == null) {
            throw new IllegalArgumentException("Caption language must be specified");
        }
        if (metadata.isDownloadable() && metadata.getSizeMB() < 1) {
            throw new IllegalArgumentException("Downloadable language must be specified");
        }
        if (metadata.getLanguage() == null || metadata.getLanguage().toString().isBlank()) {
            throw new IllegalArgumentException("Language must not be null or empty");
        }
        if (metadata.getLanguage().toString().matches(".*\\.(ENGLISH|HINDI|FRENCH|SPANISH|GERMAN|CHINESE|JAPANESE)")) {
            throw new IllegalArgumentException("Language must be inclusive of the options");
        }
    }

}
