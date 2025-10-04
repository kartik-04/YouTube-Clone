package com.youtube_clone.subscription.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "subscriptions",
        indexes = {
                @Index(name = "idx_creator_id", columnList = "creatorId"),
                @Index(name = "idx_user_creator", columnList = "userId, creatorId", unique = true)
        }
)
@Getter
@Setter
@NoArgsConstructor  // Fixes the no-arg constructor issue
@AllArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID userId;
    private UUID creatorId;
    private LocalDateTime subscribedAt;
    private boolean active;
}