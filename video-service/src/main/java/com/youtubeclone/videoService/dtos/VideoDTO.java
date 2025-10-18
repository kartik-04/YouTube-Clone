package com.youtubeclone.videoService.dtos;

public class VideoDTO {
    private String videoId;

    private String title;

    private String description;

    private  String videoUrl;

    private String thumbnailUrl;

    private String creatorId;

    private String uploadDate;

    private String visibility;

    private VideoMetadataDTO metadata;

    public VideoDTO() {
    }

    public VideoDTO(String videoId, String title,
                    String description, String videoUrl,
                    String thumbnailUrl, String creatorId,
                    String uploadDate, String visibility,
                    VideoMetadataDTO metadata) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.creatorId = creatorId;
        this.uploadDate = uploadDate;
        this.visibility = visibility;
        this.metadata = metadata;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public VideoMetadataDTO getMetadata() {
        return metadata;
    }

    public void setMetadata(VideoMetadataDTO metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "VideoDTO{" +
                "videoId='" + videoId + '\'' +
                "title='" + title + '\'' +
                "description='" + description + '\'' +
                "videoUrl='" + videoUrl + '\'' +
                "creatorId='" + creatorId + '\'' +
                "thumbnail='" + thumbnailUrl + '\'' +
                "uploadDate='" + uploadDate + '\'' +
                "Visibility='" + visibility + '\'' +
                "VideoMetadataDTO='" + metadata + '\'' +
                "}";
    }
}
