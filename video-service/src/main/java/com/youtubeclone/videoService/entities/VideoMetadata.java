package com.youtubeclone.videoService.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents metadata information about a video, such as
 * length, size, caption availability, language, and quality.
 * This class is used inside the {@link Video} model.
 */
@Entity
@Table(name = "video_metadata", indexes = {
        @Index(name = "idx_metadata_quality", columnList = "quality"),
        @Index(name = "idx_metadata_language", columnList = "language")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Duration of the video in seconds.
     * Must be a positive value.
     */
    @Column(name = "length_seconds", nullable = false)
    @Min(value = 1, message = "Video length must be at least 1 second")
    private int lengthSeconds;

    /**
     * Size of the video file in megabytes.
     * Must be a positive value.
     */
    @Column(name = "size_mb", nullable = false, precision = 10, scale = 2)
    @Positive(message = "Video size must be a positive number")
    private double sizeMB;

    /**
     * Indicates if the video has captions available.
     */
    @Column(name = "has_captions", nullable = false)
    private boolean caption;

    /**
     * Indicates if the video can be downloaded.
     */
    @Column(name = "is_downloadable", nullable = false)
    private boolean downloadable;

    /**
     * Primary language of the video content.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false, length = 20)
    private Language language;

    /**
     * Video resolution quality.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "quality", nullable = false, length = 10)
    private Quality quality;

    /**
     * Bidirectional relationship with the Video entity.
     * This is the inverse side of the relationship.
     */
    @OneToOne(mappedBy = "metadata", fetch = FetchType.LAZY)
    private Video video;
}