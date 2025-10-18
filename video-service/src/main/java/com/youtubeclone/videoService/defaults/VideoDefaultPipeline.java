package com.youtubeclone.videoService.defaults;

import com.youtubeclone.videoService.entities.Video;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Pipeline that executes multiple default rules on a video.
 * Rules are applied in the order they are added.
 */
@Slf4j
@Component
public class VideoDefaultPipeline {
    private final List<VideoDefaultRule> rules = new ArrayList<>();

    /**
     * Adds a rule to the pipeline.
     * @param rule the rule to add
     */
    public void addRule(VideoDefaultRule rule) {
        rules.add(rule);
        log.debug("Added rule: {}", rule.getClass().getSimpleName());
    }

    /**
     * Applies all registered rules to the video.
     * @param video the video to apply defaults to
     */
    public void applyDefault(Video video) {
        log.debug("Applying {} default rules to video", rules.size());
        for(VideoDefaultRule rule : rules){
            rule.apply(video);
        }
    }
}
