package com.youtubeclone.validators.video.rules;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.validators.video.ValidationRule;

import java.net.MalformedURLException;
import java.net.URL;

public class ThumbnailRule implements ValidationRule {

    public void validate(Video video) {

        if(video.getThumbnailUrl() == null || video.getThumbnailUrl().isBlank()) {
            throw new IllegalArgumentException("ThumbnailUrl cannot be null");
        }
        if(video.getThumbnailUrl().startsWith("http")) {
            throw new  IllegalArgumentException("ThumbnailUrl cannot be an HTTP url");
        }
        if(video.getThumbnailUrl().matches(".*\\.(jpg|jpeg|png|webp)$")){
            throw new  IllegalArgumentException("ThumbnailUrl must end with .jpg , .jpeg , .png or .webp");
        }
        if(video.getThumbnailUrl().length() > 500) {
            throw new IllegalArgumentException("ThumbnailUrl is too long");
        }

        try{
            new URL(video.getThumbnailUrl());
        }catch(MalformedURLException e){
            throw new IllegalArgumentException("ThumbnailUrl is not a valid URL");
        }
    }
}
