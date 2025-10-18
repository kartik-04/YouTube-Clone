package com.youtubeclone.videoService.mappers;

import com.youtubeclone.Models.video.Language;
import com.youtubeclone.Models.video.Quality;
import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Models.video.Visibility;
import com.youtubeclone.dtos.video.VideoDTO;
import com.youtubeclone.dtos.video.VideoMetadataDTO;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Utility class for converting between {@link Video} entities and {@link VideoDTO} objects.
 * <p>
 * This class is stateless and cannot be instantiated.
 * </p>
 */
public final class VideoMapper {

    /** Private constructor to prevent instantiation. */
    private VideoMapper() {}

    /**
     * Converts a {@link Video} entity into a {@link VideoDTO}.
     *
     * @param video the {@link Video} entity to convert (can be {@code null})
     * @return the corresponding {@link VideoDTO}, or {@code null} if input is {@code null}
     */
    public static VideoDTO toDTO(Video video) {
        if (video == null) {
            return null;
        }

        VideoDTO dto = new VideoDTO();

        // Map metadata if present
        VideoMetadata metadata = video.getMetadata();
        if (metadata != null) {
            VideoMetadataDTO metadataDTO = new VideoMetadataDTO();
            metadataDTO.setLengthSeconds(metadata.getLengthSeconds());
            metadataDTO.setSizeMB(metadata.getSizeMB());
            metadataDTO.setCaption(metadata.isCaption());
            metadataDTO.setDownloadable(metadata.isDownloadable());
            metadataDTO.setQuality(metadata.getQuality().name());
            metadataDTO.setLanguage(metadata.getLanguage().name());
            dto.setMetadata(metadataDTO);
        }

        // Map core video fields
        dto.setVideoId(video.getVideoId().toString());
        dto.setTitle(video.getTitle());
        dto.setThumbnailUrl(video.getThumbnailUrl());
        dto.setDescription(video.getDescription());
        dto.setUploadDate(video.getUploadDate().toString());
        dto.setVisibility(video.getVisibility().name());
        dto.setCreatorId(video.getCreatorId().toString());
        dto.setVideoUrl(video.getVideoUrl());

        return dto;
    }

    /**
     * Converts a {@link VideoDTO} object into a {@link Video} entity.
     *
     * @param dto the {@link VideoDTO} to convert (can be {@code null})
     * @return the corresponding {@link Video} entity, or {@code null} if input is {@code null}
     * @throws IllegalArgumentException if DTO contains invalid enum values for
     *                                  {@link Quality}, {@link Language}, or {@link Visibility}
     */
    public static Video toEntity(VideoDTO dto) {
        if (dto == null) {
            return null;
        }

        Video video = new Video();

        // Map core video fields
        video.setVideoId(UUID.fromString(dto.getVideoId()));
        video.setTitle(dto.getTitle());
        video.setThumbnailUrl(dto.getThumbnailUrl());
        video.setDescription(dto.getDescription());
        video.setUploadDate(LocalDate.parse(dto.getUploadDate()));
        video.setVisibility(Visibility.valueOf(dto.getVisibility()));
        video.setCreatorId(UUID.fromString(dto.getCreatorId()));
        video.setVideoUrl(dto.getVideoUrl());

        // Map metadata if present
        if (dto.getMetadata() != null) {
            VideoMetadata metadata = new VideoMetadata();
            metadata.setQuality(Quality.valueOf(dto.getMetadata().getQuality()));
            metadata.setLengthSeconds(dto.getMetadata().getLengthSeconds());
            metadata.setSizeMB(dto.getMetadata().getSizeMB());
            metadata.setCaption(dto.getMetadata().isCaption());
            metadata.setDownloadable(dto.getMetadata().isDownloadable());
            metadata.setLanguage(Language.valueOf(dto.getMetadata().getLanguage()));
            video.setMetadata(metadata);
        } else {
            video.setMetadata(null);
        }

        return video;
    }
}