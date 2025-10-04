package com.youtube_clone.subscription.validation;

import com.youtube_clone.subscription.entities.Subscription;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidationPipeline {
    private final List<ValidationRule> validationRules = new ArrayList<>();

    public void addRules(ValidationRule rule){
        validationRules.add(rule);
    }

    public void validate(Subscription subscription) {
        for(ValidationRule rule : validationRules){
            rule.validate(subscription);
        }
    }
}
