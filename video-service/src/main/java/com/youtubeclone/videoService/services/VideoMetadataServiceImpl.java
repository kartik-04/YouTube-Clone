package com.youtubeclone.videoService.services;

import com.youtubeclone.videoService.entities.Video;
import com.youtubeclone.videoService.entities.VideoMetadata;
import com.youtubeclone.videoService.exceptions.NotFoundException;
import com.youtubeclone.videoService.repositories.MetadataRepository;
import com.youtubeclone.videoService.repositories.VideoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VideoMetadataServiceImpl implements VideoMetadataService {

    private final VideoRepository videoRepository;
    private final MetadataRepository metadataRepository;

    @Override
    public Video createVideo(Video video) {
        log.info("Creating new video with title: {}", video.getTitle());
        if (video.getMetadata() == null) {
            throw new IllegalArgumentException("Video metadata cannot be null");
        }
        Video saved = videoRepository.save(video);
        log.debug("Saved video with ID: {}", saved.getId());
        return saved;
    }

    /**
     * @param videoId
     */
    @Override
    public void deleteVideo(UUID videoId) {
        log.info("Deleting video metadata for videoId: {}", videoId);
        Video video = videoRepository.findByVideoId(videoId)
                .orElseThrow(() -> new NotFoundException("Video not found with id " + videoId));
        videoRepository.delete(video);
        log.debug("Deleted video and metadata for ID: {}", videoId);
    }

    @Override
    public Video getVideoById(UUID videoId) {
        log.debug("Fetching video by ID: {}", videoId);
        return videoRepository.findByVideoId(videoId)
                .orElseThrow(() -> new NotFoundException("Video not found with ID " + videoId));
    }

    @Override
    public List<Video> getVideoByCreator(UUID creatorId) {
        log.debug("Fetching videos for creator ID: {}", creatorId);
        return videoRepository.findByCreatorId(creatorId);
    }

    @Override
    public void changeThumbnail(UUID videoId, String newThumbnail) {
        log.info("Changing thumbnail for videoId: {}", videoId);
        Video video = videoRepository.findByVideoId(videoId)
                .orElseThrow(() -> new NotFoundException("Video not found with ID " + videoId));
        video.setThumbnailUrl(newThumbnail);
        videoRepository.save(video);
        log.debug("Thumbnail updated for video ID: {}", videoId);
    }

    @Override
    public VideoMetadata getVideoMetadata(UUID videoId) {
        log.debug("Fetching metadata for videoId: {}", videoId);
        Video video = videoRepository.findByVideoId(videoId)
                .orElseThrow(() -> new NotFoundException("Video not found with ID " + videoId));
        return video.getMetadata();
    }

    @Override
    public Video getVideoByTitle(String title) {
        log.debug("Searching video by title: {}", title);
        return videoRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new NotFoundException("Video not found with title: " + title));
    }
}