package com.youtubeclone.validators.video;

import com.youtubeclone.Models.video.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * ValidationPipeline represents a sequence of validation rules
 * that are applied to a Video object. Each rule is executed in order.
 */

public class ValidationPipeline {
    private final List<ValidationRule> rules;

    /**
     * Creates an empty pipeline. Rules must be added before use.
     */
    public ValidationPipeline() {
        this.rules = new ArrayList<>();
    }

    /**
     * Adds a validation rule to the pipeline.
     *
     * @param rule the validation rule to add
     */
    public void addRule(ValidationRule rule) {
        this.rules.add(rule);
    }

    /**
     * Executes all validation rules on the provided Video.
     * If any rule fails, the rule throws an exception.
     *
     * @param video the video entity to validate
     */
    public void validate(Video video) {
        for(ValidationRule rule : rules){
            rule.validate(video);                           // If any one fail exception will be thrown
        }
    }
}
