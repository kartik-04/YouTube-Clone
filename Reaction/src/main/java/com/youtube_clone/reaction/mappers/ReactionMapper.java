package com.youtube_clone.reaction.mappers;

import com.youtube_clone.reaction.dtos.ReactionDTO;
import com.youtube_clone.reaction.entities.Reaction;
import com.youtube_clone.reaction.entities.ReactionType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ReactionMapper {
    public static ReactionDTO toDTO(Reaction reaction){
        if(reaction == null)
            return null;

        return ReactionDTO.builder()
                .userId(reaction.getUserId() != null ? reaction.getUserId().toString() : null)
                .videoId(reaction.getVideoId() != null ? reaction.getVideoId().toString() : null)
                .commentId(reaction.getCommentId() != null ? reaction.getCommentId().toString() : null)
                .type(reaction.getType() != null ? reaction.getType().name() : null)
                .active(reaction.isActive())
                .reactedAt(reaction.getReactedAt() != null ? reaction.getReactedAt().toString() : null)
                .updatedAt(reaction.getUpdatedAt() != null ? reaction.getUpdatedAt().toString() : null)
                .build();
    }

    public static Reaction toEntity(ReactionDTO dto){
        if(dto == null) return null;

        return Reaction.builder()
                .userId(dto.getUserId() != null ? UUID.fromString(dto.getUserId()) : null)
                .videoId(dto.getVideoId() != null ? UUID.fromString(dto.getVideoId()) : null)
                .commentId(dto.getCommentId() !=null ? UUID.fromString(dto.getCommentId()) : null)
                .type(dto.getType() != null ? ReactionType.valueOf(dto.getType().toUpperCase()) : null)
                .active(dto.isActive())
                .reactedAt(dto.getReactedAt() != null ? LocalDateTime.parse(dto.getReactedAt()) : null)
                .updatedAt(dto.getUpdatedAt() != null ? LocalDateTime.parse(dto.getUpdatedAt()) : null)
                .build();
    }
}
