package com.youtubeclone.defaults.video;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.defaults.video.rule.MetadataDefaultRule;
import com.youtubeclone.defaults.video.rule.TitleDefaultRule;
import com.youtubeclone.defaults.video.rule.UploadDateDefaultRule;
import com.youtubeclone.defaults.video.rule.VideoIdDefaultRule;

public class VideoDefaultApplier implements VideoDefaultRule{
    private final VideoDefaultPipeline defaultPipeline;

    public VideoDefaultApplier() {
        defaultPipeline = new VideoDefaultPipeline();
        defaultPipeline.addRule(new MetadataDefaultRule());
        defaultPipeline.addRule(new TitleDefaultRule());
        defaultPipeline.addRule(new UploadDateDefaultRule());
        defaultPipeline.addRule(new VideoIdDefaultRule());
    }

    @Override
    public void apply(Video video) {
        defaultPipeline.applyDefault(video);
    }
}
