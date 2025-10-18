package com.youtubeclone.videoService.validators;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.videoService.validators.rules.MetadataRule;
import com.youtubeclone.videoService.validators.rules.ThumbnailRule;
import com.youtubeclone.videoService.validators.rules.TitleNotNullRule;
import com.youtubeclone.videoService.validators.rules.UploadDateRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * VideoValidator wires together a set of validation rules
 * into a single pipeline. It delegates validation of a Video
 * object to the pipeline at runtime.
 */
@Component
public class VideoValidator {
    private final ValidationPipeline pipeline;

    /**
     * Constructs a VideoValidator with all required validation rules.
     */
    @Autowired
    public VideoValidator(MetadataRule metadataRule, 
                         ThumbnailRule thumbnailRule, 
                         TitleNotNullRule titleNotNullRule, 
                         UploadDateRule uploadDateRule) {
        this.pipeline = new ValidationPipeline();
        this.pipeline.addRule(metadataRule);
        this.pipeline.addRule(thumbnailRule);
        this.pipeline.addRule(titleNotNullRule);
        this.pipeline.addRule(uploadDateRule);
    }

    /**
     *  Runs the validation pipeline against the given Video object.
     *  Throws IllegalArgumentException if any rule fails.
     *
     *  This is runtime polymorphism in play as we are calling validate method using
     *  pipeline object at runtime it is deciding to call the validate method
     *  present inside the VideoPipeline class
     *
     * @param video the video entity to validate
     */
    public void validate(Video video){
        pipeline.validate(video);
    }
}
