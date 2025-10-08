package com.youtube_clone.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
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
    private String reactionType; // e.g., "like", "dislike", "love", etc.

    @Column(name="timestamp", nullable = false)
    private LocalDateTime ReactedA = LocalDateTime.now();

    @Column(name="active", nullable = false)
    private boolean active = true;      // Soft delete flag

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void deactivate(){
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }
}
