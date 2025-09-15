package com.youtubeclone.validators.comment;

import com.youtubeclone.Models.comment.Comment;

public interface CommentValidationRule {
    void validate(Comment comment);
}
