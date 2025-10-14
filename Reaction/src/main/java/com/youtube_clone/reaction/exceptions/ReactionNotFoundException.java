package com.youtube_clone.reaction.exceptions;

public class ReactionNotFoundException extends RuntimeException{
    public ReactionNotFoundException(String message){
        super(message);
    }
}
