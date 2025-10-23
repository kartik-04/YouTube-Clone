package com.youtubeclone.videoService.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.youtubeclone.videoService.entities.Visibility;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object for Video entity.
 * Used to transfer video data between client and server.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;

    @NotNull(message = "Video ID is required")
    private UUID videoId;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;

    @NotBlank(message = "Video URL is required")
    private String videoUrl;

    @NotBlank(message = "Thumbnail URL is required")
    private String thumbnailUrl;

    @NotBlank(message = "Creator ID is required")
    private UUID creatorId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Upload date is required")
    private LocalDate uploadDate;

    @NotNull(message = "Visibility is required")
    private Visibility visibility;

    @Valid
    @NotNull(message = "Metadata is required")
    private VideoMetadataDTO metadata;

    // Builder pattern customization if needed
    public static VideoDTOBuilder builder() {
        return new VideoDTOBuilder() {
            @Override
            public VideoDTO build() {
                VideoDTO dto = super.build();
                // Add any custom validation or post-processing here
                return dto;
            }
        };
    }
}