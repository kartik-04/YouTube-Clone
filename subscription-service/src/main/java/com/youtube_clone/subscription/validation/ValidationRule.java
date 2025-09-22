package com.youtube_clone.subscription.validation;

import com.youtube_clone.subscription.entities.Subscription;

public interface ValidationRule {
    void validate(Subscription subscription);
}
