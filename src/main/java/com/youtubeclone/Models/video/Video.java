package com.youtubeclone.Models.video;

import java.time.LocalDate;
import java.util.UUID;

public class Video {

    /** Choosing UUID for random generated videoId for the video */
    private final UUID videoId;

    private String title;

    private String description;

    private String videoUrl;

    private String thumbnailUrl;

    private UUID creatorId;

    private LocalDate uploadDate;

    private Visibility visibility;

    private VideoMetadata metadata;

    /** created enum for easy access */
    public enum Visibility{
        PUBLIC,
        PRIVATE,
        UNLISTED
    }

    /** Default Constructor */
    public Video(){
        this.videoId = UUID.randomUUID();
        this.title = "";
        this.description = "";
        this.videoUrl = "";
        this.thumbnailUrl = "";
        this.creatorId = UUID.randomUUID();
        this.uploadDate = LocalDate.now();
        this.visibility = Visibility.PRIVATE;
        this.metadata = new VideoMetadata();
    }

    /** Parameterized Constructor */
    public Video(UUID id, String title, String description,
                 String videoUrl, String thumbnailUrl,
                 UUID creatorId, LocalDate uploadDate,
                 Visibility visibility,
                 VideoMetadata metadata)
    {
        this.videoId = id;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.creatorId = creatorId;
        this.uploadDate = uploadDate;
        this.visibility = visibility;
        this.metadata = (metadata != null) ? new VideoMetadata(metadata) : null;
    }

    /** Copy Constructor with Deep Copy */
    public Video(Video video){
        this.videoId = video.videoId;
        this.title = video.title;
        this.description = video.description;
        this.videoUrl = video.videoUrl;
        this.thumbnailUrl = video.thumbnailUrl;
        this.creatorId = video.creatorId;
        this.uploadDate = video.uploadDate;
        this.visibility = video.visibility;

        if (video.metadata != null) {
            this.metadata = new VideoMetadata(video.metadata);
        }
    }


    /**
    Setting up the setter for the attributes,
    Does not added setter for ID cause ID is final and should not change so there
    is no need for setter for the same.
     */

    public UUID getVideoId() {
        return videoId;
    }

    /** Getter and Setter for Title */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /** Getter and Setter for Description */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** Getters and Setters for VideoUrl */
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /** Getter and Setter for Thumbnail */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /** Getter and Setter for CreatorId */
    public UUID getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId) {
        this.creatorId = creatorId;
    }

    /** Getter and Setter for uploadDate */
    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    /** Getter and Setter for the enum */
    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    /** Seeting up getter and setter for metadata as well */

    public VideoMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(VideoMetadata metadata) {
        this.metadata = (metadata != null) ? new VideoMetadata(metadata) : null;
    }

    @Override
    public String toString() {
        return "Video [videoId=" + videoId + ", " +
                "title=" + title + ", " +
                "description=" + description + ", " +
                "videoUrl=" + videoUrl+ ", " +
                "thumbnailUrl=" + thumbnailUrl + ", " +
                "creatorId=" + creatorId + ", " +
                "uploadDate=" + uploadDate + ", " +
                "visibility=" + visibility + ", " +
                "metadata=" + metadata + "]";
    }
}
