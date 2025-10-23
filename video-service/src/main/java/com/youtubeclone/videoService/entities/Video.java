package com.youtubeclone.videoService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a video entity in the system.
 * Contains core video information and relationships.
 */
@Entity
@Table(name = "videos", indexes = {
        @Index(name = "idx_video_videoid", columnList = "videoId", unique = true),
        @Index(name = "idx_video_creator", columnList = "creatorId"),
        @Index(name = "idx_video_visibility", columnList = "video_visibility"),
        @Index(name = "idx_video_upload_date", columnList = "video_uploadDate")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "videoId", nullable = false, unique = true)
    private UUID videoId;

    @Column(name = "video_title", nullable = false, length = 200)
    private String title;

    @Column(name = "video_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "videoUrl", nullable = false, length = 512)
    private String videoUrl;

    @Column(name = "thumbnail_url", nullable = false, length = 512)
    private String thumbnailUrl;

    @Column(name = "creatorId", nullable = false)
    private UUID creatorId;

    @Column(name = "video_uploadDate", nullable = false)
    private LocalDate uploadDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "video_visibility", nullable = false, length = 20)
    private Visibility visibility;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "metadata_id", referencedColumnName = "id")
    private VideoMetadata metadata;
}