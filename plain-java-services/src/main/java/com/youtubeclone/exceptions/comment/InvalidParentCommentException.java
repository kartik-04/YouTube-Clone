package com.youtubeclone.exceptions.comment;

public class InvalidParentCommentException extends RuntimeException {
    public InvalidParentCommentException(String message) {
        super(message);
    }
}
