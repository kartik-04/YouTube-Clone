package com.youtubeclone.services.Impl.video;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Models.video.VideoMetadata;
import com.youtubeclone.services.Interfaces.video.VideoMetadataService;

import java.util.List;
import java.util.UUID;

public class VideoMetadataServiceImpl implements VideoMetadataService {

    /**
     * @param video
     * @return
     */
    @Override
    public Video createVideo(Video video) {
        return null;
    }

    /**
     * @param videoId
     */
    @Override
    public void deleteVideoMetadata(UUID videoId) {

    }

    public Video getVideoById(UUID videoId) {
        return null;
    }

    /**
     * @param creatorId
     * @return
     */
    @Override
    public List<Video> getVideoByCreator(UUID creatorId) {
        return List.of();
    }

    /**
     * @param videoId
     * @param newThumbnail
     */
    @Override
    public void changeThumbnail(UUID videoId, String newThumbnail) {

    }

    /**
     * @param videoId
     * @return
     */
    @Override
    public VideoMetadata getVideoMetadata(UUID videoId) {
        return null;
    }

    /**
     * @param title
     * @return
     */
    @Override
    public Video getVideoByTitle(String title) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
