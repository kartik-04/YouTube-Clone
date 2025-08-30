package com.youtubeclone.services.Interfaces.video;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Models.video.VideoMetadata;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface IVideoFileService {

    void uploadVideo(UUID videoId,  InputStream fileData);

    byte[] downloadVideo(Video video);

    InputStream streamVideo(UUID videoId);

    void deleteVideoFile(UUID videoId);
}
