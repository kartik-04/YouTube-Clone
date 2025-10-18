package com.youtubeclone.videoService.defaults;

import com.youtubeclone.videoService.defaults.rules.MetadataDefaultRule;
import com.youtubeclone.videoService.defaults.rules.TitleDefaultRule;
import com.youtubeclone.videoService.defaults.rules.UploadDateDefaultRule;
import com.youtubeclone.videoService.defaults.rules.VideoIdDefaultRule;
import com.youtubeclone.videoService.entities.Video;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service that applies default values to videos using a pipeline of rules.
 * All rules are automatically injected and registered during initialization.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VideoDefaultApplier implements VideoDefaultRule {
    private final VideoDefaultPipeline defaultPipeline;
    private final MetadataDefaultRule metadataDefaultRule;
    private final TitleDefaultRule titleDefaultRule;
    private final UploadDateDefaultRule uploadDateDefaultRule;
    private final VideoIdDefaultRule videoIdDefaultRule;

    /**
     * Initializes the pipeline with all default rules.
     * Called automatically after dependency injection.
     */
    @PostConstruct
    public void init() {
        log.info("Initializing VideoDefaultApplier with default rules");
        defaultPipeline.addRule(videoIdDefaultRule);
        defaultPipeline.addRule(titleDefaultRule);
        defaultPipeline.addRule(uploadDateDefaultRule);
        defaultPipeline.addRule(metadataDefaultRule);
        log.info("VideoDefaultApplier initialized successfully");
    }

    /**
     * Applies all default rules to the given video.
     * @param video the video to apply defaults to
     */
    @Override
    public void apply(Video video) {
        log.debug("Applying defaults to video: {}", video.getVideoId());
        defaultPipeline.applyDefault(video);
    }
}
