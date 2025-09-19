package com.youtubeclone.Models.comment;

import java.time.LocalDate;
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
 * Clients are encouraged to use {@link Comment#getBuilder()} to construct instances.
 */
public class Comment {

    /** Unique identifier for this comment. */
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


    // =========================
    // Getters
    // =========================

    /**
     * @return the unique identifier of this comment
     */
    public UUID getCommentId() {
        return commentId;
    }

    /**
     * @return the identifier of the video this comment belongs to
     */
    public UUID getVideoId() {
        return videoId;
    }

    /**
     * @return the identifier of the user who posted this comment
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * @return the parent comment ID if this is a reply, otherwise {@code null}
     */
    public UUID getParentId() {
        return parentId;
    }

    /**
     * @return the textual content of this comment
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the timestamp of when this comment was created
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * @return the current status of the comment
     */
    public CommentStatus getStatus() {
        return status;
    }

    /**
     * @return the number of dislikes for this comment
     */
    public int getDislikes() {
        return dislikes;
    }

    /**
     * @return the number of likes for this comment
     */
    public int getLikes() {
        return likes;
    }

    /**
     * @return the list of past versions of this comment
     */
    public List<String> getEditHistory() {
        return editHistory;
    }


    // =========================
    // Setters (used in service layer if mutation is required)
    // =========================

    public void setCommentId(UUID commentId) {
        this.commentId = commentId;
    }

    public void setVideoId(UUID videoId) {
        this.videoId = videoId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public void setEditHistory(List<String> editHistory) {
        this.editHistory = editHistory;
    }


    // =========================
    // Constructors
    // =========================

    /** Default constructor (required for frameworks such as Jackson or JPA). */
    public Comment() { }

    /**
     * Creates a new {@code Comment} using explicit parameters.
     * This constructor is private to encourage use of the {@link Builder}.
     *
     * @param commentId    unique comment identifier
     * @param videoId      video identifier this comment belongs to
     * @param userId       user identifier of the author
     * @param parentId     parent comment identifier, if this is a reply
     * @param content      comment text
     * @param timestamp    creation date of the comment
     * @param status       current status of the comment
     * @param likes        number of likes
     * @param dislikes     number of dislikes
     * @param editHistory  list of prior comment versions
     */
    private Comment(UUID commentId, UUID videoId, UUID userId, UUID parentId,
                    String content, LocalDateTime timestamp, CommentStatus status,
                    int likes, int dislikes, List<String> editHistory) {
        this.commentId = commentId;
        this.videoId = videoId;
        this.userId = userId;
        this.parentId = parentId;
        this.content = content;
        this.timestamp = timestamp;
        this.status = status;
        this.likes = likes;
        this.dislikes = dislikes;
        this.editHistory = editHistory;
    }

    /**
     * Private constructor used by the {@link Builder}.
     *
     * @param builder the builder object containing comment properties
     */
    private Comment(Builder builder) {
        this.commentId = builder.commentId;
        this.videoId = builder.videoId;
        this.userId = builder.userId;
        this.parentId = builder.parentId;
        this.content = builder.content;
        this.timestamp = builder.timestamp;
        this.status = builder.status;
        this.likes = builder.likes;
        this.dislikes = builder.dislikes;
        this.editHistory = builder.editHistory;
    }


    // =========================
    // Builder
    // =========================

    /**
     * Provides access to the {@link Builder} for constructing {@code Comment} objects.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Builder class for constructing {@link Comment} objects.
     * <p>
     * Example usage:
     * <pre>
     *     Comment comment = Comment.getBuilder()
     *          .setVideoId(videoId)
     *          .setUserId(userId)
     *          .setContent("Great video!")
     *          .setTimestamp(LocalDate.now())
     *          .setStatus(CommentStatus.ACTIVE)
     *          .build();
     * </pre>
     */
    public static class Builder {
        private UUID commentId;
        private UUID videoId;
        private UUID userId;
        private UUID parentId;
        private String content;
        private LocalDateTime timestamp;
        private CommentStatus status;
        private int likes;
        private int dislikes;
        private List<String> editHistory;

        public Builder setCommentId(UUID commentId) {
            this.commentId = commentId;
            return this;
        }

        public Builder setVideoId(UUID videoId) {
            this.videoId = videoId;
            return this;
        }

        public Builder setUserId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public Builder setParentId(UUID parentId) {
            this.parentId = parentId;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setStatus(CommentStatus status) {
            this.status = status;
            return this;
        }

        public Builder setLikes(int likes) {
            this.likes = likes;
            return this;
        }

        public Builder setDislikes(int dislikes) {
            this.dislikes = dislikes;
            return this;
        }

        public Builder setEditHistory(List<String> editHistory) {
            this.editHistory = editHistory;
            return this;
        }

        /**
         * Builds and returns a new {@link Comment} instance.
         *
         * @return a new immutable {@link Comment}
         */
        public Comment build() {
            return new Comment(this);
        }
    }
}