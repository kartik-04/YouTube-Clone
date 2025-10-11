package com.youtube_clone.reaction.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReactionRequest {

    @NotNull(message = "UserId cannot be null")
    private String userId;

    private String videoId;
    private String commentId;

    @NotNull(message = "ReactionType cannot be null")
    private String type; // e.g., LIKE, DISLIKE
}