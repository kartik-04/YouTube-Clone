package com.youtubeclone.commentService.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Represents a user-generated comment on a video.
 * <p>
 * A {@code Comment} may be a top-level comment (when {@link #parentId} is {@code null})
 * or a reply to another comment (when {@link #parentId} is set).
 * Each comment maintains metadata such as author, creation timestamp,
 * edit history, and like/dislike counts.
 * <p>
 * This class follows the Builder design pattern for flexible object creation.
 * Clients are encouraged to use {@link Comment# getBuilder()} to construct instances.
 */
@Entity
@Table(name = "Comment-service", schema = "comment_service", indexes = {
        @Index(name = "comment_id", columnList = "comment_id", unique = true),
        @Index(name = "video_id", columnList = "video_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Comment {

    /** Unique identifier for this comment. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID commentId;

    /** Identifier of the video that this comment belongs to. */
    private UUID videoId;

    /** Identifier of the user who posted the comment. */
    private UUID userId;

    /** Identifier of the parent comment if this is a reply, otherwise {@code null}. */
    private UUID parentId;

    /** The textual content of the comment. */
    private String content;

    /** The date when the comment was created. */
    private LocalDateTime timestamp;

    /** Current status of the comment (e.g., active, deleted, flagged). */
    private CommentStatus status;

    /** Number of likes received by the comment. */
    private int likes;

    /** Number of dislikes received by the comment. */
    private int dislikes;

    /** List of previous versions of the comment content. */
    private List<String> editHistory;
}