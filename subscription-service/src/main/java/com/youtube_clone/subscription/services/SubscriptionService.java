package com.youtube_clone.subscription.services;


import com.youtube_clone.subscription.entities.Subscription;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {
    Subscription subscribe(UUID userId, UUID creatorId);
    void  unsubscribe(UUID userId, UUID creatorId);
    List<Subscription> getSubscribersByUserId(UUID userId);
    List<Subscription> getSubscribersByCreatorId(UUID creatorId);
    boolean isUserSubscribed(UUID userId, UUID creatorId);
    int getSubscriberCount(UUID creatorId);
    Page<Subscription> getSubscribersByCreatorId(UUID creatorId, int page, int size);
}
