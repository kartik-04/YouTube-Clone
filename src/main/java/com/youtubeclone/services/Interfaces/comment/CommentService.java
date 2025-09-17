package com.youtubeclone.services.Interfaces.comment;

import com.youtubeclone.Models.comment.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {
     void addComment(Comment comment);
     void deleteComment(UUID commentId);
    Comment getCommentById(UUID commentId);
    List<Comment> getCommentsByVideo(UUID videoId);
    List<Comment> getReplies(UUID parentId);
    Comment editComment(UUID commentId, String newCommentText);
    void likeComment(UUID commentId, UUID userId);
    void dislikeComment(UUID commentId, UUID userId);
    void flagComment(UUID commentId, String reason);
    List<Comment> getCommentThread(UUID videoId);
    List<Comment> getCommentByVideo(UUID videoId, int page, int size, String sortBy);
}
