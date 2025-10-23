package com.youtubeclone.videoService.controllers;

import com.youtubeclone.videoService.defaults.VideoDefaultApplier;
import com.youtubeclone.videoService.dtos.CreateVideoRequest;
import com.youtubeclone.videoService.dtos.VideoDTO;
import com.youtubeclone.videoService.entities.Video;
import com.youtubeclone.videoService.entities.VideoMetadata;
import com.youtubeclone.videoService.exceptions.ApiResponse;
import com.youtubeclone.videoService.exceptions.NotFoundException;
import com.youtubeclone.videoService.exceptions.RateLimitExceededException;
import com.youtubeclone.videoService.mappers.VideoMapper;
import com.youtubeclone.videoService.ratelimit.RateLimiter;
import com.youtubeclone.videoService.services.VideoMetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
@Slf4j
@Validated
public class VideoController {

    private final VideoMetadataService videoMetadataService;
    private final VideoDefaultApplier videoDefaultApplier;
    private final RateLimiter fixedRateLimiter;

    @Operation(summary = "Upload a new video", description = "Creates a new video with metadata details.")
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<VideoDTO>> createVideo(
            @RequestBody @Validated CreateVideoRequest request
    ) {
        log.info("Received video upload request for title: {}", request.getTitle());

        VideoMetadata metadata = VideoMetadata.builder()
                .lengthSeconds(request.getLengthSeconds())
                .sizeMB(request.getSizeMB())
                .caption(request.isCaption())
                .downloadable(request.isDownloadable())
                .language(request.getLanguage())
                .quality(request.getQuality())
                .build();

        Video video = Video.builder()
                .videoId(UUID.randomUUID())
                .title(request.getTitle())
                .description(request.getDescription())
                .videoUrl(request.getVideoUrl())
                .thumbnailUrl(request.getThumbnailUrl())
                .creatorId(request.getCreatorId())
                .uploadDate(java.time.LocalDate.now())
                .visibility(request.getVisibility())
                .metadata(metadata)
                .build();

        if (fixedRateLimiter.allowRequest(video.getCreatorId())) {
            throw new RateLimitExceededException("Upload limit reached for this creator");
        }

        videoDefaultApplier.apply(video);
        Video saved = videoMetadataService.createVideo(video);
        log.debug("Video created successfully: {}", saved.getTitle());
        return ResponseEntity.ok(ApiResponse.success("Video created successfully", VideoMapper.toDTO(saved)));
    }

    @Operation(summary = "Get video by ID")
    @GetMapping("/{videoId}")
    public ResponseEntity<ApiResponse<VideoDTO>> getVideoById(
            @Parameter(description = "Unique ID of the video") @PathVariable UUID videoId
    ) {
        Video video = videoMetadataService.getVideoById(videoId);
        if (video == null) {
            throw new NotFoundException("Video not found for id: " + videoId);
        }

        if (fixedRateLimiter.allowRequest(videoId)) {
            throw new RateLimitExceededException("Too many requests for this video");
        }
        return ResponseEntity.ok(ApiResponse.success("Video found", VideoMapper.toDTO(video)));
    }

    @Operation(summary = "Get all videos by creator")
    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<ApiResponse<List<VideoDTO>>> getVideosByCreator(
            @PathVariable UUID creatorId
    ) {
        List<VideoDTO> videos = videoMetadataService.getVideoByCreator(creatorId)
                .stream()
                .map(VideoMapper::toDTO)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Videos fetched successfully", videos));
    }

    @Operation(summary = "Update video thumbnail")
    @PatchMapping("/{videoId}/thumbnail")
    public ResponseEntity<ApiResponse<Void>> updateThumbnail(
            @PathVariable UUID videoId,
            @RequestParam String newThumbnail
    ) {
        videoMetadataService.changeThumbnail(videoId, newThumbnail);
        return ResponseEntity.ok(ApiResponse.success("Thumbnail updated successfully", null));
    }

    @Operation(summary = "Delete a video by ID")
    @DeleteMapping("/{videoId}")
    public ResponseEntity<ApiResponse<Void>> deleteVideo(@PathVariable UUID videoId) {
        videoMetadataService.deleteVideo(videoId);
        return ResponseEntity.ok(ApiResponse.success("Video deleted successfully", null));
    }
}