package com.youtubeclone.defaults.comment.rules;


import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.defaults.comment.CommentDefaultRule;

import java.util.UUID;

public class CommentIdDefaultRule implements CommentDefaultRule {
    @Override
    public void apply(Comment comment) {
        if (comment.getCommentId() == null) {
            comment.setCommentId(UUID.randomUUID());
        }
    }
}
