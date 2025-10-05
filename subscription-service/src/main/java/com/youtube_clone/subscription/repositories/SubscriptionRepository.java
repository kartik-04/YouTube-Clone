package com.youtube_clone.subscription.repositories;

import com.youtube_clone.subscription.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    List<Subscription> findByUserId(UUID userId);
    List<Subscription> findByCreatorId(UUID creatorId);
    Optional<Subscription> findByUserIdAndCreatorId(UUID userId, UUID creatorId);
    Page<Subscription> findByCreatorId(UUID creatorId, Pageable pageable);
    int countByCreatorIdAndActiveTrue(UUID creatorId);
}
