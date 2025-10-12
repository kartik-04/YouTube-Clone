package com.youtube_clone.reaction.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "UserId cannot be blank")
    private String userId;

    private String videoId;
    private String commentId;

    @NotNull(message = "ReactionType cannot be null")
    @NotBlank(message = "ReactionType cannot be blank")
    @Pattern(
            regexp = "LIKE|DISLIKE|LOVE|SAD|ANGRY",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Invalid reaction type"
    )
    private String type; // e.g., LIKE, DISLIKE
}