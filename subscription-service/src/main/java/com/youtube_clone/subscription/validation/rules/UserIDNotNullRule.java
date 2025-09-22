package com.youtube_clone.subscription.validation.rules;

import com.youtube_clone.subscription.entities.Subscription;
import com.youtube_clone.subscription.exceptions.InvalidSubscriptionException;
import com.youtube_clone.subscription.validation.ValidationRule;

public class UserIDNotNullRule implements ValidationRule {
    /** This method make sure that Id of the user is not null
     * @param subscription object of Subscription
     */
    @Override
    public void validate(Subscription subscription) {
        if(subscription.getUserId() == null){
            throw new InvalidSubscriptionException("Subscription's user ID cannot be null");
        }
    }
}
