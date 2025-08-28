package com.youtubeclone.validators.video;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.validators.video.rules.MetadataRule;
import com.youtubeclone.validators.video.rules.ThumbnailRule;
import com.youtubeclone.validators.video.rules.TitleNotNullRule;
import com.youtubeclone.validators.video.rules.UploadDateRule;

/**
 * VideoValidator wires together a set of validation rules
 * into a single pipeline. It delegates validation of a Video
 * object to the pipeline at runtime.
 */

public class VideoValidator{
    private final ValidationPipeline pipeline;

    /**
     * Constructs a VideoValidator and configures the pipeline
     * with all required rules (e.g., metadata, title, thumbnail).
     */
    public VideoValidator(){
       pipeline = new ValidationPipeline();
       pipeline.addRule(new MetadataRule());
       pipeline.addRule(new ThumbnailRule());
       pipeline.addRule(new TitleNotNullRule());
       pipeline.addRule(new UploadDateRule());
    }

    /**
     *  Runs the validation pipeline against the given Video object.
     *  Throws IllegalArgumentException if any rule fails.
     *
     *  This is runtime polymorphism is play as we are calling validate method using
     *  pipeline object at runtime it is deciding to call the validate method
     *  present inside the VideoPipeline class
     *
     * @param video the video entity to validate
     */
    public void validate(Video video){
        pipeline.validate(video);
    }
}
