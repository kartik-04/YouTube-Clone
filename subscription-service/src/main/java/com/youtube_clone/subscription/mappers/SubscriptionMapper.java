package com.youtube_clone.subscription.mappers;

import com.youtube_clone.subscription.dtos.CreateSubscriptionRequest;
import com.youtube_clone.subscription.dtos.SubscriptionDTO;
import com.youtube_clone.subscription.entities.Subscription;

import java.util.UUID;

public final class SubscriptionMapper {

    private SubscriptionMapper() {} // Utility class, no instantiation

    /**
     * Maps CreateSubscriptionRequest to Subscription entity.
     * Fills only the fields provided by client. Other fields like id and timestamps
     * can be set in service layer.
     */
    public static Subscription toEntity(CreateSubscriptionRequest request) {
        if (request == null) return null;

        return Subscription.builder()
                .userId(UUID.fromString(request.getUserId()))
                .creatorId(UUID.fromString(request.getCreatorId()))
                .build();
    }

    /**
     * Maps Subscription entity to SubscriptionDTO for API response.
     */
    public static SubscriptionDTO toDTO(Subscription subscription) {
        if (subscription == null) return null;

        return SubscriptionDTO.builder()
                .userId(String.valueOf(subscription.getUserId()))
                .creatorId(String.valueOf(subscription.getCreatorId()))
                .subscribedAt(String.valueOf(subscription.getSubscribedAt()))
                .active(subscription.isActive())
                .build();
    }
}