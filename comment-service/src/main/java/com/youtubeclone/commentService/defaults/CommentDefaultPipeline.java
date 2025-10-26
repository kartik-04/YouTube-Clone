package com.youtubeclone.commentService.defaults;

import com.youtubeclone.Models.comment.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentDefaultPipeline {
    private final List<CommentDefaultRule> rules = new ArrayList<>();

    public void addRules(CommentDefaultRule rule) {
        rules.add(rule);
    }

    public void applyDefaults(Comment comment) {
        for(CommentDefaultRule rule : rules) {
            rule.apply(comment);
        }
    }
}
