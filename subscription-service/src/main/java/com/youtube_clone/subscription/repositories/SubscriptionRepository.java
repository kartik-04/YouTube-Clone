package com.youtube_clone.subscription.repositories;

import com.youtube_clone.subscription.entities.Subscription;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Registered
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    List<Subscription> findByUserId(UUID userId);
    List<Subscription> findByCreatorId(UUID creatorId);
    Optional<Subscription> findByUserIdAndCreatorId(UUID userId, UUID creatorId);
}
