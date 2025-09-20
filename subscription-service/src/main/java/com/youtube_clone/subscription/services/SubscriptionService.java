package com.youtube_clone.subscription.services;


import com.youtube_clone.subscription.entities.Subscription;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {
    Subscription subscribe(UUID userId, UUID creatorId);
    void  unsubscribe(UUID userId, UUID creatorId);
    List<Subscription> getSubscribersByUserId(UUID userId);
    List<Subscription> getSubscribersByCreatorId(UUID creatorId);
    boolean isUserSubscribed(UUID userId, UUID creatorId);
}
