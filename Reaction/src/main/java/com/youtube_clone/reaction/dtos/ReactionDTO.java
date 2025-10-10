package com.youtube_clone.reaction.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionDTO {

    @NotBlank(message = "userid cannot be blank or empty")
    private String userId;

    @NotBlank(message = "videoId or commentId must be provided")
    private String videoId;
    private String commentId;

    @NotBlank(message = "ReactionType must be provided")
    @Pattern(regexp = "LIKE|DISLIKE|LOVE|ANGRY|SADI" , message = "ReactionType must be LIKE, DISLIKE, LOVE, ANGRY or SADI")
    private String type;

    private boolean active;
    private String reactedAt;
    private String updatedAt;
}
