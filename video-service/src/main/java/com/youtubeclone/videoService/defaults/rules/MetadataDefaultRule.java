package com.youtubeclone.videoService.defaults.rules;

import com.youtubeclone.Models.video.Quality;
import com.youtubeclone.Models.video.Video;
import com.youtubeclone.Models.video.VideoMetadata;
import com.youtubeclone.defaults.video.VideoDefaultRule;

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
