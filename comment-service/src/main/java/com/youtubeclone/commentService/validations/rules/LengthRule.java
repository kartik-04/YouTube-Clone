package com.youtubeclone.commentService.validations.rules;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.exceptions.ValidationException;
import com.youtubeclone.validators.comment.CommentValidationRule;

public class LengthRule implements CommentValidationRule {
    private static final int MAX_LENGTH = 5000;

    /** It helps to use the object of comment where we check if
     * comment is empty or not.
     * @param comment object of Comment class
     */
    @Override
    public void validate(Comment comment) {
        if(comment.getContent() != null && comment.getContent().length() > MAX_LENGTH) {
            throw new ValidationException("Comment exceeds maximum length of " + MAX_LENGTH + " characters");
        }
    }
}