package com.youtubeclone.validators.comment.rules;

import com.youtubeclone.Models.comment.Comment;
import com.youtubeclone.exceptions.ValidationException;
import com.youtubeclone.validators.comment.CommentValidationRule;

public class ContentNotEmptyRule implements CommentValidationRule {

    /** This class make sure to check if the content is empty or null
     * @param comment
     */
    @Override
    public void validate(Comment comment) {
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new ValidationException("Comment content cannot be empty");
        }
    }
}