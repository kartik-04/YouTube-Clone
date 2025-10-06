package com.youtube_clone.reaction.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "reactions",
    indexes = {
        @Index (name = "idx_user_video", columnList = "userId, videoId", unique = true),
            @Index(name = "idx_video_id", columnList = "videoId"),
            @Index(name = "idx_comment_id", columnList = "commentId")
    },uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_video", columnNames = {"user_id", "video_id"}),
        @UniqueConstraint(name = "uk_user_comment", columnNames = {"user_id", "comment_id"})
}
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "video_id")
    private UUID videoId;

    @Column(name = "comment_id")
    private UUID commentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type", nullable = false)
    private ReactionType reactionType; // LIKE, DISLIKE, LOVE, etc.

    @Column(name = "active", nullable = false)
    private boolean active = true; // soft delete

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Optional: convenience method to mark inactive
    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }
}
