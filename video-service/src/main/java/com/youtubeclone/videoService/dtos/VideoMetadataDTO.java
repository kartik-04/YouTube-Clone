package com.youtubeclone.videoService.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youtubeclone.videoService.entities.Language;
import com.youtubeclone.videoService.entities.Quality;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Data Transfer Object for VideoMetadata.
 * Used to transfer video metadata between client and server.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoMetadataDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;

    @NotNull(message = "Length in seconds is required")
    @Min(value = 1, message = "Length must be at least 1 second")
    private Integer lengthSeconds;

    @NotNull(message = "Size in MB is required")
    @Positive(message = "Size must be a positive number")
    private Double sizeMB;

    @NotNull(message = "Caption availability must be specified")
    private Boolean Captions;

    @NotNull(message = "Downloadable status must be specified")
    private Boolean Downloadable;

    @NotNull(message = "Language is required")
    private Language language;

    @NotNull(message = "Quality is required")
    private Quality quality;

    // Add any additional DTO-specific methods or validations here
}