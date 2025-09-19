package com.youtubeclone.defaults.video.rule;

import com.youtubeclone.Models.video.Video;
import com.youtubeclone.defaults.video.VideoDefaultRule;

import java.time.LocalDate;

public class UploadDateDefaultRule  implements VideoDefaultRule {
    @Override
    public void apply(Video video) {
        if(video.getUploadDate() == null){
            video.setUploadDate(LocalDate.now());
        }
    }
}
