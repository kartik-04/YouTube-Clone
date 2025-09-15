package com.youtubeclone.defaults.comment.rules;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.defaults.comment.CommentDefaultRule;

import java.time.LocalDate;

public class TimestampDefaultRule implements CommentDefaultRule {
    @Override
    public void apply(Comment comment) {
        if (comment.getTimestamp() == null) {
            comment.setTimestamp(LocalDate.now());
        }
    }
}
