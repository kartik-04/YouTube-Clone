package com.youtubeclone.commentService.controllers;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.dtos.comment.CommentDTO;
import com.youtubeclone.mappers.comment.CommentMapper;
import com.youtubeclone.services.Interfaces.comment.CommentService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // ---- Create ----
    public void addComment(CommentDTO dto) {
        Comment comment = CommentMapper.toEntity(dto);
        commentService.addComment(comment);
    }

    // ---- Read ----
    public CommentDTO getCommentById(String commentId) {
        Comment comment = commentService.getCommentById(UUID.fromString(commentId));
        return CommentMapper.toDTO(comment);
    }

    public List<CommentDTO> getCommentsByVideo(String videoId) {
        return commentService.getCommentsByVideo(UUID.fromString(videoId))
                .stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getReplies(String parentId) {
        return commentService.getReplies(UUID.fromString(parentId))
                .stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getCommentThread(String videoId) {
        return commentService.getCommentThread(UUID.fromString(videoId))
                .stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getCommentByVideo(String videoId, int page, int size, String sortBy) {
        return commentService.getCommentByVideo(UUID.fromString(videoId), page, size, sortBy)
                .stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ---- Update ----
    public CommentDTO editComment(String commentId, String newText) {
        Comment comment = commentService.editComment(UUID.fromString(commentId), newText);
        return CommentMapper.toDTO(comment);
    }

    public void likeComment(String commentId, String userId) {
        commentService.likeComment(UUID.fromString(commentId), UUID.fromString(userId));
    }

    public void dislikeComment(String commentId, String userId) {
        commentService.dislikeComment(UUID.fromString(commentId), UUID.fromString(userId));
    }

    public void flagComment(String commentId, String reason) {
        commentService.flagComment(UUID.fromString(commentId), reason);
    }

    // ---- Delete ----
    public void deleteComment(String commentId) {
        commentService.deleteComment(UUID.fromString(commentId));
    }
}