package com.youtube_clone.subscription.validation.rules;

import com.youtube_clone.subscription.entities.Subscription;
import com.youtube_clone.subscription.exceptions.InvalidSubscriptionException;
import com.youtube_clone.subscription.validation.ValidationRule;

public class CannotSubscribeSelfRule implements ValidationRule {
    /** It makes sure that Creator is not able to subscribe to itself
     * @param subscription object of Subscription
     */
    @Override
    public void validate(Subscription subscription) {
        if(subscription.getUserId().equals(subscription.getCreatorId())){
            throw new InvalidSubscriptionException("User cannot Subscribe to itself");
        }
    }
}
