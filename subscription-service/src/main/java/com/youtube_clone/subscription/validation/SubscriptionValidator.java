package com.youtube_clone.subscription.validation;

import com.youtube_clone.subscription.entities.Subscription;
import com.youtube_clone.subscription.validation.rules.AlreadySubscribedRule;
import com.youtube_clone.subscription.validation.rules.CannotSubscribeSelfRule;
import com.youtube_clone.subscription.validation.rules.CreatorIdNotNullRule;
import com.youtube_clone.subscription.validation.rules.UserIDNotNullRule;

public class SubscriptionValidator {
    private final ValidationPipeline pipeline;

    public SubscriptionValidator(ValidationPipeline pipeline) {
        this.pipeline = pipeline;
        pipeline.addRules(new AlreadySubscribedRule());
        pipeline.addRules(new CannotSubscribeSelfRule());
        pipeline.addRules(new CreatorIdNotNullRule());
        pipeline.addRules(new UserIDNotNullRule());
    }

    public void validate(Subscription subscription) {
        pipeline.validate(subscription);
    }
}
