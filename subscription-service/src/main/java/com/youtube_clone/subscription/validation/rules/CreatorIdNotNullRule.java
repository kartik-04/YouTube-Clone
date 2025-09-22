package com.youtube_clone.subscription.validation.rules;

import com.youtube_clone.subscription.entities.Subscription;
import com.youtube_clone.subscription.validation.ValidationRule;

public class CreatorIdNotNullRule implements ValidationRule {

    /** This makes sure that creator Id is not null
     * @param subscription object of Subscription.
     */
    @Override
    public void validate(Subscription subscription) {
        if(subscription.getCreatorId() == null) {
            throw new IllegalArgumentException("creatorId cannot be null");
        }
    }
}
