package com.youtubeclone.Models.video;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Models.video.VideoMetadata;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class VideoValidator {

    private static final Pattern VIDEO_URL_PATTERN = Pattern.compile("^https://.*$");

    private static final Pattern THUMBNAIL_URL_PATTERN =
            Pattern.compile("^https://.*\\.(jpg|jpeg|png)$");

    public static void validate(Video video) throws IllegalArgumentException {

        if (video.getTitle() == null || video.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }

        // Validate URL
        if (video.getVideoUrl() == null || !VIDEO_URL_PATTERN.matcher(video.getVideoUrl()).matches()) {
            throw new IllegalArgumentException("Invalid video URL. Must start with https://");
        }

        // Validate upload date
        if (video.getUploadDate() == null) {
            throw new IllegalArgumentException("Upload date cannot be null.");
        }
        if (video.getUploadDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Upload date cannot be in the future.");
        }

        // 3. Thumbnail validation
        if (video.getThumbnailUrl() != null &&
                !THUMBNAIL_URL_PATTERN.matcher(video.getThumbnailUrl()).matches()) {
            throw new IllegalArgumentException("Thumbnail must be a valid image URL (.jpg, .jpeg, .png)");
        }

        // 4. Date validation
        if (video.getUploadDate() != null && video.getUploadDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Upload date cannot be in the future");
        }

        // 5. Metadata validation (if present)
        if (video.getMetadata() != null) {
            validateMetadata(video.getMetadata());
        }
    }

    private static void validateMetadata(VideoMetadata metadata) {

        if (metadata.getLengthSeconds() <= 0) {
            throw new IllegalArgumentException("Video length must be greater than 0 seconds");
        }

        if (metadata.getSizeMB() <= 0) {
            throw new IllegalArgumentException("Video size must be greater than 0 MB");
        }

        if (metadata.getQuality() == null) {
            throw new IllegalArgumentException("Video quality cannot be null or empty");
        }

        if (metadata.getLanguage() == null) {
            throw new IllegalArgumentException("Video language cannot be null or empty");
        }
    }
}
