package com.youtubeclone.commentService.defaults.rules;


import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.Models.comment.CommentStatus;
import com.youtubeclone.defaults.comment.CommentDefaultRule;

public class StatusDefaultRule implements CommentDefaultRule {
    @Override
    public void apply(Comment comment) {
        if (comment.getStatus() == null) {
            comment.setStatus(CommentStatus.ACTIVE);
        }
    }
}
