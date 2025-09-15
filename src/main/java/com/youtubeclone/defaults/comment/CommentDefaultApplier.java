package com.youtubeclone.defaults.comment;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.defaults.comment.rules.CommentIdDefaultRule;
import com.youtubeclone.defaults.comment.rules.StatusDefaultRule;
import com.youtubeclone.defaults.comment.rules.TimestampDefaultRule;

public class CommentDefaultApplier implements CommentDefaultRule {
    private final CommentDefaultPipeline pipeline;

    public CommentDefaultApplier() {
        this.pipeline = new CommentDefaultPipeline();
        pipeline.addRules(new CommentIdDefaultRule());
        pipeline.addRules(new TimestampDefaultRule());
        pipeline.addRules(new StatusDefaultRule());
    }

    @Override
    public void apply(Comment comment) {
        pipeline.applyDefaults(comment);
    }
}
