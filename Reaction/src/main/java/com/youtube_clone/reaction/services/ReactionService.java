package com.youtube_clone.reaction.services;

import com.youtube_clone.reaction.entities.Reaction;
import com.youtube_clone.reaction.entities.ReactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;
public interface ReactionService {
    Reaction reactToVideo(UUID userId, UUID videoId, ReactionType type);
    Reaction reactToComment(UUID userId, UUID commentId, ReactionType type);
    void removeReactionFromVideo(UUID userId, UUID videoId);
    void removeReactionFromComment(UUID userId, UUID commentId);
    Map<ReactionType, Long> getReactionCountForVideo(UUID videoId);
    Map<ReactionType, Long> getReactionCountForComment(UUID commentId);
    Reaction getUserReactionForVideo(UUID userId, UUID videoId);
    Reaction getUserReactionForComment(UUID userId, UUID commentId);
    Page<Reaction> getReactionForVideo(UUID videoId, Pageable pageable);
    Page<Reaction> getReactionForComment(UUID commentId, Pageable pageable);
}
