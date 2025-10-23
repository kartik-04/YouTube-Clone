package com.youtubeclone.videoService.dtos;

import com.youtubeclone.videoService.entities.Language;
import com.youtubeclone.videoService.entities.Quality;
import com.youtubeclone.videoService.entities.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateVideoRequest {
    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String videoUrl;

    @NotBlank
    private String thumbnailUrl;

    @NotNull
    private UUID creatorId;

    @NotNull
    private Visibility visibility;

    @Positive
    private int lengthSeconds;

    @Positive
    private double sizeMB;

    private boolean caption;
    private boolean downloadable;

    @NotNull
    private Language language;

    @NotNull
    private Quality quality;
}