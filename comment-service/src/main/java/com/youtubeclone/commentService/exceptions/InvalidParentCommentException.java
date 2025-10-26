package com.youtubeclone.commentService.exceptions;

public class InvalidParentCommentException extends RuntimeException {
    public InvalidParentCommentException(String message) {
        super(message);
    }
}
