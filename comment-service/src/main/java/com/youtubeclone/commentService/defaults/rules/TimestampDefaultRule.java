package com.youtubeclone.commentService.defaults.rules;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.defaults.comment.CommentDefaultRule;

import java.time.LocalDateTime;

public class TimestampDefaultRule implements CommentDefaultRule {
    @Override
    public void apply(Comment comment) {
        if (comment.getTimestamp() == null) {
            comment.setTimestamp(LocalDateTime.now());
        }
    }
}
