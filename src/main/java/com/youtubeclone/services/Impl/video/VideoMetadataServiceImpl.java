package com.youtubeclone.services.Impl.video;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Models.video.VideoMetadata;
import com.youtubeclone.defaults.video.VideoDefaultApplier;
import com.youtubeclone.validators.video.VideoValidator;
import com.youtubeclone.Repositories.video.MetadataRepository;
import com.youtubeclone.services.Interfaces.video.VideoMetadataService;

import java.util.List;
import java.util.UUID;

public class VideoMetadataServiceImpl implements VideoMetadataService {

    MetadataRepository repository;
    VideoValidator videoValidator;
    VideoDefaultApplier defaultApplier;


    public VideoMetadataServiceImpl(MetadataRepository repo, VideoValidator videoValidator, VideoDefaultApplier defaultApplier) {
        this.repository = repo;
        this.videoValidator = videoValidator;
        this.defaultApplier = defaultApplier;
    }

    /**
     * This uses the default pipeline which
     * has the diff rules and check
     * in the pipeline and checking if defaults are set or not
     * if not it assign the default values to it first.
     * After default, it goes through the validate video object for the diff checks
     * Saves the video to the in memory storage for the metadata
     * @param video of the Video
     * @return video of Video
     */
    @Override
    public Video createVideo(Video video) {
        defaultApplier.apply(video);
        videoValidator.validate(video);
        repository.save(video);

        return video;
    }

    /**
     * @param videoId for the search of Video
     */
    @Override
    public void deleteVideoMetadata(UUID videoId) {
        repository.delete(videoId);
    }

    /** Over here we are creating the helper
     * method in the interface of metadataRepository.java
     * where we make sure to check for the null value
     * check to maintain ocp and src
     * @param videoId of UUID is id for the video
     * @return video object
     */
    public Video getVideoById(UUID videoId) {
        return repository.findById(videoId);
    }

    /**
     * this method is using the ensureExists
     * method in the Repository for the
     * metadata
     * @param creatorId is not linked to the VideoId which is stored in the repository of metadata
     * @return List<Video> list of Video object
     */
    @Override
    public List<Video> getVideoByCreator(UUID creatorId) {
        return repository.findByCreator(creatorId);
    }

    /**
     *  change the thumbnail to new one using the defaultApplier, repository
     * @param videoId is of UUID type
     * @param newThumbnail of String type
     */
    @Override
    public void changeThumbnail(UUID videoId, String newThumbnail) {
        Video video = repository.findById(videoId);
        defaultApplier.apply(video);
        video.setThumbnailUrl(newThumbnail);
        repository.save(video);
    }

    /**
     * @param videoId is of type videoId
     * @return VideoMetadata object
     */
    @Override
    public VideoMetadata getVideoMetadata(UUID videoId) {
        Video video = repository.findById(videoId);
        defaultApplier.apply(video);
        return video.getMetadata();
    }

    /**
     * @param title is of String type
     * @return object of Video
     */
    @Override
    public Video getVideoByTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        Video video = repository.findByTitle(title);
        defaultApplier.apply(video);
        return video;
    }

}
