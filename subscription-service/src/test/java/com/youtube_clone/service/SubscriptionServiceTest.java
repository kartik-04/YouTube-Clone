package com.youtube_clone.service;

import com.youtube_clone.subscription.entities.Subscription;
import com.youtube_clone.subscription.repositories.SubscriptionRepository;
import com.youtube_clone.subscription.services.SubscriptionService;
import com.youtube_clone.subscription.services.SubscriptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(SubscriptionServiceImpl.class)
public class SubscriptionServiceTest {

    SubscriptionRepository repository;

    @Autowired
    private SubscriptionService subscriptionService;

    private UUID userId;
    private UUID creatorId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        creatorId = UUID.randomUUID();
    }

    @Test
    void subscribe_ShouldCreateNewSubscription() {
        Subscription sub = subscriptionService.subscribe(userId, creatorId);

        assertThat(sub).isNotNull();
        assertThat(sub.isActive()).isTrue();
        assertThat(repository.findByUserIdAndCreatorId(userId, creatorId)).isPresent();
    }

    @Test
    void unsubscribe_ShouldMarkSubscriptionInactive() {
        subscriptionService.subscribe(userId, creatorId);

        subscriptionService.unsubscribe(userId, creatorId);

        Subscription sub = repository.findByUserIdAndCreatorId(userId, creatorId).get();
        assertThat(sub.isActive()).isFalse();  // not deleted, just inactive
    }

    @Test
    void isUserSubscribed_ShouldReturnTrueIfActive() {
        subscriptionService.subscribe(userId, creatorId);

        boolean result = subscriptionService.isUserSubscribed(userId, creatorId);

        assertThat(result).isTrue();
    }

    @Test
    void repository_FindByUserIdAndCreatorId_ShouldReturnEmptyIfNotSubscribed() {
        assertThat(repository.findByUserIdAndCreatorId(userId, creatorId)).isEmpty();
    }
}
