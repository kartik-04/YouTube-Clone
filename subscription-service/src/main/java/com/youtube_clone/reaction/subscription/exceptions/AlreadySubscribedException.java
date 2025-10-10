package com.youtube_clone.reaction.subscription.exceptions;

public class AlreadySubscribedException extends RuntimeException {
    public AlreadySubscribedException(String message) {
        super(message);
    }
}
