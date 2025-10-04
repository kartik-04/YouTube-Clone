package com.youtube_clone.subscription.services;

import com.youtube_clone.subscription.entities.Subscription;
import com.youtube_clone.subscription.repositories.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({SubscriptionServiceImpl.class, TestConfig.class})
class SubscriptionServiceTest {

    @Autowired
    private SubscriptionServiceImpl subscriptionService;

    @Autowired
    private SubscriptionRepository repository;

    @Test
    void testSubscribeAndCheckIsUserSubscribed() {
        UUID userId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();

        subscriptionService.subscribe(userId, creatorId);

        assertThat(subscriptionService.isUserSubscribed(userId, creatorId)).isTrue();
    }

    @Test
    void testUnsubscribe() {
        UUID userId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();

        subscriptionService.subscribe(userId, creatorId);
        subscriptionService.unsubscribe(userId, creatorId);

        var sub = repository.findByUserIdAndCreatorId(userId, creatorId).orElseThrow();
        assertThat(sub.isActive()).isFalse();
    }

    @Test
    void testGetSubscribersByCreatorId() {
        UUID creatorId = UUID.randomUUID();
        subscriptionService.subscribe(UUID.randomUUID(), creatorId);
        subscriptionService.subscribe(UUID.randomUUID(), creatorId);

        List<Subscription> subscribers = subscriptionService.getSubscribersByCreatorId(creatorId);
        assertThat(subscribers).hasSize(2);
    }
}