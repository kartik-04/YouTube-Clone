// src/test/java/com/youtube_clone/subscription/services/TestConfig.java
package com.youtube_clone.subscription.services;

import com.youtube_clone.subscription.validation.SubscriptionValidator;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public SubscriptionValidator subscriptionValidator() {
        return Mockito.mock(SubscriptionValidator.class);
    }
}
