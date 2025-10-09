package com.youtube_clone.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(
        name = "reactions",
        indexes = {
                @Index(name = "idx_video_id", columnList = "video_id"),
                @Index(name = "idx_comment_id", columnList = "comment_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_video", columnNames = {"user_id", "video_id"}),
                @UniqueConstraint(name = "uk_user_comment", columnNames = {"user_id", "comment_id"})
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="user_id", nullable = false)
    private UUID userId;

    @Column(name="video_id", nullable = false)
    private UUID videoId;

    @Column(name="comment_id", nullable = false)
    private  UUID commentId;

    @Enumerated(EnumType.STRING)
    @Column(name="reaction_type", nullable = false)
    private ReactionType type; // e.g., "like", "dislike", "love", etc.

    @Column(name="timestamp", nullable = false)
    private LocalDateTime ReactedAt = LocalDateTime.now();

    @Column(name="active", nullable = false)
    private boolean active = true;      // Soft delete flag

    @UpdateTimestamp    // Automatically updates the updatedAt field
    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Soft delete the reaction by setting the active flag to false and
     * updating the updatedAt field to the current timestamp.
     */
    public void deactivate(){
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }
}

