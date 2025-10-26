package com.youtubeclone.commentService.repositories;

import com.youtubeclone.Models.comment.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentRepository {
    void save(Comment comment);
    void delete(UUID commentId);
    Comment findById(UUID commentId);
    List<Comment> findByVideoID(UUID commentId);
    List<Comment> findByCommentId(UUID commentId);
}
