package com.youtubeclone.services.Interfaces.video;

import com.youtubeclone.Models.user.Creator;
import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Models.video.VideoMetadata;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface IVideoService {

    Video createVideo(Video video);

    void uploadVideo(UUID videoId);

    byte[] downloadVideo(Video video);

    void deleteVideo(Video video);

    InputStream streamVideo(UUID videoId);

    Video getVideoById(UUID videoId);

    List<Video> getVideoByCreator(UUID creatorId);

    void updateThumbnail(UUID videoId, String newThumbnail);

    VideoMetadata getVideoMetadata(UUID videoId);

    Video getVideoByTitle(String title);
}
