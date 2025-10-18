package com.youtubeclone.videoService.repositories;

import com.youtubeclone.videoService.entities.Video;
import com.youtubeclone.videoService.exceptions.NotFoundException;
import com.youtubeclone.videoService.exceptions.ValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MetadataRepository {
    Map<UUID, Video> storage = new HashMap<>();

    public void save(Video video) {
        storage.put(video.getVideoId(), video);
    }

    public Video findById(UUID id) {
        ensureExists(id);
        return storage.get(id);
    }

    public List<Video> findByCreator(UUID creatorId) {
        return storage.values().stream()
                .filter(v -> v.getCreatorId().equals(creatorId))
                .toList();
    }

    public void delete(UUID id) {
        ensureExists(id);
        storage.remove(id);
    }

    public void ensureExists(UUID videoId) {
        Video video = storage.get(videoId);
        if(video == null) {
            throw new NotFoundException("Video with Id " + videoId + " not exists in metadata");
        }
    }

    public Video findByTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new ValidationException("Title cannot be null or empty");
        }

        return storage.values().stream()
                .filter(v -> v.getTitle() != null && v.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null); // or throw exception if you want strictness
    }
}
