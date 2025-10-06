package com.youtube_clone.reaction.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Table(name = "reactions",
    indexes = {
            @Index(name = "idx_video_id", columnList = "videoId"),
            @Index(name = "idx_comment_id", columnList = "commentId"),
            @Index(name = "idx_user_video", columnList="userId, videoId", unique = true)
    }
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID userId;
    private UUID videoId;  // Nullable if the reaction is on a comment
    private UUID commentId; // Nullable if the reaction is on a video
}