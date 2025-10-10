package com.youtube_clone.reaction.subscription.validation.rules;

import com.youtube_clone.reaction.subscription.entities.Subscription;
import com.youtube_clone.reaction.subscription.exceptions.AlreadySubscribedException;
import com.youtube_clone.reaction.subscription.repositories.SubscriptionRepository;
import com.youtube_clone.reaction.subscription.validation.ValidationRule;

public class AlreadySubscribedRule implements ValidationRule {
    /** Checks for the user id if already subscribed to the creatorId
     * @param subscription object of Subscriber
     */
    SubscriptionRepository subscriptionRepository;

    @Override
    public void validate(Subscription subscription) {
        subscriptionRepository.findByUserIdAndCreatorId(subscription.getUserId(), subscription.getCreatorId())
                .ifPresent(existing ->{
                    if(existing.isActive()) {
                        throw new AlreadySubscribedException(
                                "User is already subscribed to this creator."
                        );
                    }
                });
    }
}
