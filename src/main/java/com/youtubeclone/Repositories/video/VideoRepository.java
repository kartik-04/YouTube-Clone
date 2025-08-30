package com.youtubeclone.Repositories.video;

import com.youtubeclone.Models.video.Video;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VideoRepository {
    Map<UUID, Video> storage = new HashMap<>();

    public void save(Video video) {
        storage.put(video.getVideoId(), video);
    }

    public Video findById(UUID id) {
        return storage.get(id);
    }

    public List<Video> findByCreator(UUID creatorId) {
        return storage.values().stream()
                .filter(v -> v.getCreatorId().equals(creatorId))
                .toList();
    }

    public void delete(UUID id) {
        storage.remove(id);
    }
}
