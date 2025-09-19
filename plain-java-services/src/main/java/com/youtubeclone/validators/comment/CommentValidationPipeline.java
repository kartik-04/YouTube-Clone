package com.youtubeclone.validators.comment;

import com.youtubeclone.Models.comment.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentValidationPipeline {
    private final List<CommentValidationRule> rules = new ArrayList<>();

    public void addRules(CommentValidationRule rule) {
        rules.add(rule);
    }

    public void validate(Comment comment) {
        for(CommentValidationRule rule : rules){
            rule.validate(comment);
        }
    }
}
