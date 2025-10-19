package com.youtubeclone.videoService.services;


import com.youtubeclone.videoService.entities.Video;
import com.youtubeclone.videoService.entities.VideoMetadata;

import java.util.List;
import java.util.UUID;

public interface VideoMetadataService {

    Video createVideo(Video video);

    void deleteVideoMetadata(UUID videoId);

    Video getVideoById(UUID videoId);

    List<Video> getVideoByCreator(UUID creatorId);

    void changeThumbnail(UUID videoId, String newThumbnail);

    VideoMetadata getVideoMetadata(UUID videoId);

    Video getVideoByTitle(String title);
}
