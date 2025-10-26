package com.youtubeclone.commentService.validations;

import com.youtubeclone.Models.comment.Comment;

public interface CommentValidationRule {
    void validate(Comment comment);
}
