package com.youtubeclone.videoService.defaults.rules;

import com.youtubeclone.videoService.defaults.VideoDefaultRule;
import com.youtubeclone.videoService.entities.Quality;
import com.youtubeclone.videoService.entities.Video;
import com.youtubeclone.videoService.entities.VideoMetadata;
import org.springframework.stereotype.Component;

/**
 * Rule to apply default values to video metadata.
 * Sets default quality, caption, downloadable status, etc.
 */
@Component
public class MetadataDefaultRule implements VideoDefaultRule {
    VideoMetadata metadata = new VideoMetadata();

    @Override
    public void apply(Video video) {
        if(metadata.getSizeMB() <= 0) {
            metadata.setSizeMB(0.0);
        }

        if(metadata.getLengthSeconds() <= 0){
            metadata.setLengthSeconds(0);
        }

        if(!metadata.isCaption()){
            metadata.setCaption(true);
        }

        if(!metadata.isDownloadable()){
            metadata.setDownloadable(true);
        }

        if(metadata.getQuality() == null){
            metadata.setQuality(Quality.P360);
        }
    }
}
