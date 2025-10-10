package com.youtube_clone.reaction.services;

import com.youtube_clone.reaction.entities.Reaction;
import com.youtube_clone.reaction.entities.ReactionType;
import com.youtube_clone.reaction.repositories.ReactionRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReactionServiceImpl implements ReactionService{


    private final ReactionRepository reactionRepository;

    /**
     * Creates or updates a user's reaction to a video.
     * If an existing reaction is found for the (userId, videoId), its type is updated, and the
     * reaction timestamp is refreshed; otherwise, a new active reaction is created and persisted.
     *
     * @param userId the unique identifier of the user reacting to the video
     * @param videoId the unique identifier of the video being reacted to
     * @param type the type of reaction (LIKE, DISLIKE, etc.)
     * @return the created or updated Reaction entity
     */
    @Override
    public Reaction reactToVideo(UUID userId, UUID videoId, ReactionType type) {
        Optional<Reaction> existing = reactionRepository.findByUserIdAndVideoId(userId,videoId);
        if(existing.isPresent()){
            Reaction r = existing.get();
            r.setUpdatedAt(LocalDateTime.now());
            r.setType(type);
            return reactionRepository.save(r);
        }

        Reaction reaction = Reaction.builder()
                .userId(userId)
                .videoId(videoId)
                .type(type)
                .reactedAt(LocalDateTime.now())
                .active(true)
                .build();
        return reactionRepository.save(reaction);
    }

    /**
     * Creates or updates a user's reaction to a comment.
     * If an existing reaction is found for the (userId, commentId), its type is updated and the
     * reaction timestamp is refreshed; otherwise, a new active reaction is created and persisted.
     *
     * @param userId the unique identifier of the user reacting to the comment
     * @param commentId the unique identifier of the comment being reacted to
     * @param type the type of reaction (LIKE, DISLIKE, etc.)
     * @return the created or updated Reaction entity
     */
    @Override
    public Reaction reactToComment(UUID userId, UUID commentId, ReactionType type) {
        Optional<Reaction> existing = reactionRepository.findByUserIdAndCommentId(userId,commentId);
        if(existing.isPresent()){
            Reaction r = existing.get();
            r.setUpdatedAt(LocalDateTime.now());
            r.setType(type);
            return reactionRepository.save(r);
        }
        Reaction reaction = Reaction.builder()
                .userId(userId)
                .commentId(commentId)
                .type(type)
                .reactedAt(LocalDateTime.now())
                .active(true)
                .build();
        return reactionRepository.save(reaction);
    }

    /**
     * Removes a user's reaction from a video.
     * Performs a soft delete by marking the reaction as inactive and updating its timestamp.
     *
     * @param userId the unique identifier of the user whose reaction should be removed
     * @param videoId the unique identifier of the video from which to remove the reaction
     */
    @Override
    public void removeReactionFromVideo(UUID userId, UUID videoId) {
        reactionRepository.findByUserIdAndVideoId(userId,videoId)
                .ifPresent(r -> {
                    r.deactivate();
                    reactionRepository.save(r);
                });
    }

    /**
     * Removes a user's reaction from a comment.
     * Performs a soft delete by marking the reaction as inactive and updating its timestamp.
     *
     * @param userId the unique identifier of the user whose reaction should be removed
     * @param commentId the unique identifier of the comment from which to remove the reaction
     */
    @Override
    public void removeReactionFromComment(UUID userId, UUID commentId) {
        reactionRepository.findByUserIdAndCommentId(userId,commentId)
                .ifPresent(r -> {
                    r.deactivate();
                    reactionRepository.save(r);
                });
    }

    /**
     * Retrieves the count of each reaction type for a specific video.
     * Aggregates reactions by type at the repository layer and returns counts.
     *
     * @param videoId the unique identifier of the video
     * @return a Map where keys are ReactionType and values are the count of each reaction type
     */
    @Override
    @Transactional(readOnly = true)
    public Map<ReactionType, Long> getReactionCountForVideo(UUID videoId) {
        List<Object[]> result = reactionRepository.countReactionsByVideoId(videoId);
        return   result.stream()
                .collect(Collectors.toMap(
                        row -> (ReactionType) row[0],
                        row -> (Long) row[1]
                ));
    }

    /**
     * Retrieves the count of each reaction type for a specific comment.
     * Aggregates reactions by type at the repository layer and returns counts.
     *
     * @param commentId the unique identifier of the comment
     * @return a Map where keys are ReactionType and values are the count of each reaction type
     */
    @Override
    @Transactional(readOnly = true)
    public Map<ReactionType, Long> getReactionCountForComment(UUID commentId) {
        List<Object[]> result = reactionRepository.countReactionByCommentId(commentId);
          return result.stream()
                        .collect(Collectors.toMap(
                        row -> ((ReactionType) row[0]),
                        row -> ((Long) row[1])
                ));
    }

    /**
     * Retrieves a specific user's reaction to a video.
     * Returns null if the user has not reacted to the video.
     *
     * @param userId the unique identifier of the user
     * @param videoId the unique identifier of the video
     * @return the user's Reaction to the video, or null if no reaction exists
     */
    @Override
    @Transactional(readOnly = true)
    public Reaction getUserReactionForVideo(UUID userId, UUID videoId) {
        return reactionRepository.findByUserIdAndVideoId(userId,videoId).orElse(null);
    }

    /**
     * Retrieves a specific user's reaction to a comment.
     * Returns null if the user has not reacted to the comment.
     *
     * @param userId the unique identifier of the user
     * @param commentId the unique identifier of the comment
     * @return the user's Reaction to the comment, or null if no reaction exists
     */
    @Override
    @Transactional(readOnly = true)
    public Reaction getUserReactionForComment(UUID userId, UUID commentId) {
       return reactionRepository.findByUserIdAndCommentId(userId,commentId).orElse(null);
    }

    /**
     * Retrieves a paginated list of all reactions for a specific video.
     *
     * @param videoId the unique identifier of the video
     * @param pageable pagination parameters (page number, size, sorting)
     * @return a Page containing the reactions for the specified page with pagination metadata
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Reaction> getReactionForVideo(UUID videoId, Pageable pageable) {
        return reactionRepository.findByVideoIdAndActiveTrue(videoId, pageable);
    }

    /**
     * Retrieves a paginated list of all reactions for a specific comment.
     *
     * @param commentId the unique identifier of the comment
     * @param pageable pagination parameters (page number, size, sorting)
     * @return a Page containing the reactions for the specified page with pagination metadata
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Reaction> getReactionForComment(UUID commentId, Pageable pageable) {
        return reactionRepository.findByCommentIdAndActiveTrue(commentId,pageable);
    }
}
