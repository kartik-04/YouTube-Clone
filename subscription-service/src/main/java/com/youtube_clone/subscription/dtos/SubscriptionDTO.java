package com.youtube_clone.subscription.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data               // it gives the getter, setters, toString(), hashcode(), equals() as well
@Builder
public class SubscriptionDTO {
    private String userId;
    private String creatorId;
    private boolean active;
    private String subscribedAt;
}
