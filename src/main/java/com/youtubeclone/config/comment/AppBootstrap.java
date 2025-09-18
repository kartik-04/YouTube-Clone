package com.youtubeclone.config.comment;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.Models.comment.CommentStatus;
import com.youtubeclone.defaults.comment.CommentDefaultApplier;
import com.youtubeclone.Repositories.comment.CommentRepositoryImpl;
import com.youtubeclone.services.Impl.comment.CommentServiceImpl;
import com.youtubeclone.services.Interfaces.comment.CommentService;
import com.youtubeclone.validators.comment.CommentValidator;
import com.youtubeclone.controllers.comment.CommentController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class AppBootstrap {

    private final CommentController commentController;

    public AppBootstrap() {
        // --- Core Dependencies ---
        CommentRepositoryImpl commentRepository = new CommentRepositoryImpl();
        CommentValidator commentValidator = new CommentValidator();
        CommentDefaultApplier defaultApplier = new CommentDefaultApplier();

        // --- Service Layer ---
        CommentService commentService = new CommentServiceImpl(
                commentRepository,
                commentValidator,
                defaultApplier
        );

        // --- Controller Layer ---
        this.commentController = new CommentController(commentService);

        // --- Seed some dummy data ---
        seedData(commentService);
    }

    private void seedData(CommentService commentService) {
        Comment comment1 = Comment.getBuilder()
                .setCommentId(UUID.randomUUID())
                .setVideoId(UUID.randomUUID())
                .setUserId(UUID.randomUUID())
                .setContent("This video is awesome!")
                .setTimestamp(LocalDateTime.now())
                .setStatus(CommentStatus.ACTIVE)
                .setLikes(12)
                .setDislikes(0)
                .setEditHistory(Arrays.asList("Initial comment"))
                .build();

        Comment comment2 = Comment.getBuilder()
                .setCommentId(UUID.randomUUID())
                .setVideoId(comment1.getVideoId()) // same video
                .setUserId(UUID.randomUUID())
                .setParentId(comment1.getCommentId()) // reply
                .setContent("Totally agree!")
                .setTimestamp(LocalDateTime.now())
                .setStatus(CommentStatus.ACTIVE)
                .setLikes(5)
                .setDislikes(1)
                .build();

        commentService.addComment(comment1);
        commentService.addComment(comment2);
    }

    public CommentController getCommentController() {
        return commentController;
    }
}