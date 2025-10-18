package com.youtubeclone.videoService.entities;


/**
 * Represents metadata information about a video, such as
 * length, size, caption availability, language, and quality.
 * This class is used inside the {@link Video} model.
 */

public class VideoMetadata {

    /** Creating the metadata for the video
     * for example like length,
     * quality, language , caption
     */
    private int lengthSeconds;

    private double sizeMB;

    private boolean caption;

    private boolean downloadable;

    private Language language;

    private Quality quality;


    /** Default constructor
     * sets the values of the videoMetadata to it's
     * default values
     * */

    public VideoMetadata(){
        this.lengthSeconds = 0;
        this.sizeMB = 0;
        this.caption = false;
        this.downloadable = false;
        this.language = Language.ENGLISH;
        this.quality = Quality.P360;
    }

    /** Parameterized Constructor */

    public VideoMetadata(int lengthSeconds, double sizeMB, boolean caption, boolean downloadable, Language language, Quality quality) {
        this.lengthSeconds = lengthSeconds;
        this.sizeMB = sizeMB;
        this.caption = caption;
        this.downloadable = downloadable;
        this.language = language;
        this.quality = quality;
    }

    /** copy constructor */

    public VideoMetadata(VideoMetadata videoMetadata) {
        this.lengthSeconds = videoMetadata.lengthSeconds;
        this.sizeMB = videoMetadata.sizeMB;
        this.caption = videoMetadata.caption;
        this.downloadable = videoMetadata.downloadable;
        this.language = videoMetadata.language;
        this.quality = videoMetadata.quality;
    }

    /** setting up the getter and setter for the private fields */

    public int getLengthSeconds() {
        return this.lengthSeconds;
    }

    public  void setLengthSeconds(int lengthSeconds) {
        this.lengthSeconds = lengthSeconds;
    }

    /** Need to create this getter and setter
     * cause require to access this in the
     * helper pipeline
     * @return size of the video
     */
    public double getSizeMB() {
        return this.sizeMB;
    }

    public void setSizeMB(double sizeMB) {
        this.sizeMB = sizeMB;
    }


    /**
     * Sets whether captions are available.
     *
     */

    public boolean isCaption() {
        return this.caption;
    }

    public void setCaption(boolean caption) {
        this.caption = caption;
    }

    /**
     * gets the downloadable for the video
     *  sets the state for downloading.
     *
     * @return downloadable true if download is possible, false otherwise
     */

    public boolean isDownloadable() {
        return this.downloadable;
    }


    public void setDownloadable(boolean downloadable) {
        this.downloadable = downloadable;
    }

    /**
     * @return language enum
     */
    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * Gets the quality of the video.
     *
     * @return the quality enum value (e.g., {@link Quality#P720})
     */
    public Quality getQuality() {
        return this.quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

}
