package com.youtubeclone.commentService.validations;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.validators.comment.rules.ContentNotEmptyRule;
import com.youtubeclone.validators.comment.rules.LengthRule;
import com.youtubeclone.validators.comment.rules.ProfanityRule;

public class CommentValidator {
    CommentValidationPipeline pipeline;

    public CommentValidator(){
        pipeline = new CommentValidationPipeline();
        pipeline.addRules(new ContentNotEmptyRule());
        pipeline.addRules(new LengthRule());
        pipeline.addRules(new ProfanityRule());
    }

    public void validate(Comment comment) {
        pipeline.validate(comment);
    }
}
