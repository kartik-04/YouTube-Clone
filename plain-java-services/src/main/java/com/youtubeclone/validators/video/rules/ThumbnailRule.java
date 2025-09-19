package com.youtubeclone.validators.video.rules;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.exceptions.ValidationException;
import com.youtubeclone.validators.video.ValidationRule;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class ThumbnailRule implements ValidationRule {

    public void validate(Video video) {

        if(video.getThumbnailUrl() == null || video.getThumbnailUrl().isBlank()) {
            throw new ValidationException("ThumbnailUrl cannot be null");
        }
        if(!video.getThumbnailUrl().startsWith("http")) {
            throw new ValidationException("ThumbnailUrl cannot be an HTTP url");
        }
        if(!video.getThumbnailUrl().matches(".*\\.(jpg|jpeg|png|webp)$")){
            throw new ValidationException("ThumbnailUrl must end with .jpg , .jpeg , .png or .webp");
        }
        if(video.getThumbnailUrl().length() > 500) {
            throw new ValidationException("ThumbnailUrl is too long");
        }

        try{
            new URL(video.getThumbnailUrl());
        }catch(MalformedURLException e){
            throw new ValidationException("ThumbnailUrl is not a valid URL", e);
        }
    }
}
