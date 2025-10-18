package com.youtubeclone.videoService.dtos;

public class VideoMetadataDTO {

    private int lengthSeconds;

    private double sizeMB;

    private boolean caption;

    private boolean downloadable;

    private String language;

    private String quality;

    public VideoMetadataDTO() {
    }

    public VideoMetadataDTO(int lengthSeconds, double sizeMB, boolean caption,
                            boolean downloadable, String language, String quality) {
        this.lengthSeconds = lengthSeconds;
        this.sizeMB = sizeMB;
        this.caption = caption;
        this.downloadable = downloadable;
        this.language = language;
        this.quality = quality;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isDownloadable() {
        return downloadable;
    }

    public void setDownloadable(boolean downloadable) {
        this.downloadable = downloadable;
    }

    public boolean isCaption() {
        return caption;
    }

    public void setCaption(boolean caption) {
        this.caption = caption;
    }

    public double getSizeMB() {
        return sizeMB;
    }

    public void setSizeMB(double sizeMB) {
        this.sizeMB = sizeMB;
    }

    public int getLengthSeconds() {
        return lengthSeconds;
    }

    public void setLengthSeconds(int lengthSeconds) {
        this.lengthSeconds = lengthSeconds;
    }

    @Override
    public String toString() {
        return "VideoMetadataDTO {" +
                "lengthSeconds='" +  lengthSeconds + '\'' +
                "sizeMB='" +  sizeMB + '\'' +
                "caption='"   + caption + '\'' +
                "downloadable='" +  downloadable + '\'' +
                "language='" +  language + '\'' +
                "quality='" + quality + '\'' +
                "} ";
    }
}
