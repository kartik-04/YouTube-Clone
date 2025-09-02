package com.youtubeclone.controllers.video;

import com.youtubeclone.Models.video.VideoMetadata;
import com.youtubeclone.dtos.video.VideoDTO;
import com.youtubeclone.dtos.video.VideoMetadataDTO;
import com.youtubeclone.mappers.video.VideoMapper;
import com.youtubeclone.Models.video.Video;
import com.youtubeclone.services.Interfaces.video.VideoMetadataService;
import com.youtubeclone.services.Interfaces.video.VideoFileService;

import java.util.List;
import java.util.UUID;

/**
 * Acts as the entry point for video-related operations.
 * In a real Spring Boot setup, this will become a REST Controller.
 * For now, it simulates handling DTOs and delegating to services.
 */
public class VideoController {

    private final VideoMetadataService metadataService;
    private final VideoFileService fileService;

    public VideoController(VideoMetadataService metadataService, VideoFileService fileService) {
        this.metadataService = metadataService;
        this.fileService = fileService;
    }

    /**
     * Create a new video (metadata only).
     * @param dto The incoming video DTO
     * @return saved video DTO with generated IDs/defaults
     */
    public VideoDTO createVideo(VideoDTO dto) {
        Video video = VideoMapper.toEntity(dto);
        Video saved = metadataService.createVideo(video);
        return VideoMapper.toDTO(saved);
    }

    /**
     * Delete a video by ID.
     * @param videoId UUID of the video
     */
    public void deleteVideo(UUID videoId) {
        metadataService.deleteVideoMetadata(videoId);
        fileService.deleteVideoFile(videoId);
    }

    /**
     * Get video metadata by ID.
     * @param videoId UUID of the video
     * @return video DTO
     */
    public VideoDTO getVideo(UUID videoId) {
        Video video = metadataService.getVideoById(videoId);
        return VideoMapper.toDTO(video);
    }

    /**
     * Get all videos by a creator.
     * @param creatorId UUID of the creator
     * @return list of DTOs
     */
    public List<VideoDTO> getVideosByCreator(UUID creatorId) {
        return metadataService.getVideoByCreator(creatorId)
                .stream()
                .map(VideoMapper::toDTO)
                .toList();
    }

    /**
     * Update a video thumbnail.
     * @param videoId UUID of the video
     * @param newThumbnail new thumbnail URL
     */
    public void updateThumbnail(UUID videoId, String newThumbnail) {
        metadataService.changeThumbnail(videoId, newThumbnail);
    }

    /**
     * Upload actual video file (binary data).
     * @param videoId UUID of the video
     * @param fileData binary file
     */
    public void uploadFile(UUID videoId, byte[] fileData) {
        fileService.uploadVideo(videoId, fileData);
    }

    /**
     * Download a video file.
     * @param videoId UUID of the video
     * @return binary data
     */
    public byte[] downloadFile(UUID videoId) {
        return fileService.downloadVideo(videoId);
    }


    /**
     * Fetch metadata details for a video.
     *
     * @param videoId the unique identifier of the video
     * @return the video metadata object
     */
    public VideoMetadata getVideoMetadata(UUID videoId) {
        return metadataService.getVideoMetadata(videoId);
    }

    /**
     * Find a video by its title.
     *
     * @param title the video title
     * @return the video as a DTO
     */
    public VideoDTO getVideoByTitle(String title) {
        var video = metadataService.getVideoByTitle(title);
        return VideoMapper.toDTO(video);
    }
}