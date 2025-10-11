package com.youtube_clone.reaction.unit;

import com.youtube_clone.reaction.entities.Reaction;
import com.youtube_clone.reaction.entities.ReactionType;
import com.youtube_clone.reaction.repositories.ReactionRepository;
import com.youtube_clone.reaction.services.ReactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReactionServiceImplTest {

    @Mock
    private ReactionRepository reactionRepository;

    @InjectMocks
    private ReactionServiceImpl reactionService;

    private UUID userId;
    private UUID commentId;
    private UUID videoId;
    private Reaction reaction;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        videoId = UUID.randomUUID();
        commentId = UUID.randomUUID();
        reaction = Reaction.builder()
                .userId(userId)
                .videoId(videoId)
                .commentId(commentId)
                .type(ReactionType.LIKE)
                .active(true)
                .build();
    }

    @ParameterizedTest
    @EnumSource(ReactionType.class)
    void shouldCheckIfTheGivenReactionIsRightOrNot(ReactionType type){
        when(reactionRepository.findByUserIdAndVideoId(userId, videoId)).thenReturn(Optional.empty());
        when(reactionRepository.save(any(Reaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reaction saved = reactionService.reactToVideo(userId,videoId,type);

        assertNotNull(saved);
        assertEquals(type, saved.getType());
        verify(reactionRepository, times(1)).save(any(Reaction.class));
    }

    // ✅ 2. Test: reactToVideo (existing reaction update)
    @Test
    void shouldUpdateExistingReactionForVideo() {
        Reaction existing = Reaction.builder()
                .userId(userId)
                .videoId(videoId)
                .type(ReactionType.DISLIKE)
                .build();

        when(reactionRepository.findByUserIdAndVideoId(userId, videoId)).thenReturn(Optional.of(existing));
        when(reactionRepository.save(any(Reaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reaction updated = reactionService.reactToVideo(userId, videoId, ReactionType.LIKE);

        assertEquals(ReactionType.LIKE, updated.getType());
        verify(reactionRepository, times(1)).save(existing);
    }

    // ✅ 3. Test: reactToComment (new comment reaction)
    @Test
    void shouldCreateReactionForComment() {
        when(reactionRepository.findByUserIdAndCommentId(userId, commentId)).thenReturn(Optional.empty());
        when(reactionRepository.save(any(Reaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reaction saved = reactionService.reactToComment(userId, commentId, ReactionType.LOVE);

        assertNotNull(saved);
        assertEquals(ReactionType.LOVE, saved.getType());
        verify(reactionRepository, times(1)).save(any(Reaction.class));
    }

    // ✅ 4. Test: removeReactionFromVideo
    @Test
    void shouldDeactivateReactionForVideo() {
        when(reactionRepository.findByUserIdAndVideoId(userId, videoId))
                .thenReturn(Optional.of(reaction));

        when(reactionRepository.save(any(Reaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        reactionService.removeReactionFromVideo(userId, videoId);

        verify(reactionRepository, times(1)).save(any(Reaction.class));
        assertFalse(reaction.isActive());
    }

    // ✅ 5. Test: getReactionCountForVideo
    @Test
    void shouldReturnReactionCountsForVideo() {
        List<Reaction> reactions = List.of(
                Reaction.builder().type(ReactionType.LIKE).build(),
                Reaction.builder().type(ReactionType.LIKE).build(),
                Reaction.builder().type(ReactionType.DISLIKE).build()
        );
        doReturn(List.of(
                new Object[] { ReactionType.LIKE, 2L },
                new Object[] { ReactionType.DISLIKE, 1L }
        )).when(reactionRepository).countReactionsByVideoId(any(UUID.class));

        Map<ReactionType, Long> counts = reactionService.getReactionCountForVideo(videoId);

        assertEquals(2L, counts.get(ReactionType.LIKE));
        assertEquals(1L, counts.get(ReactionType.DISLIKE));
    }

    // ✅ 6. Test: getUserReactionForVideo
    @Test
    void shouldReturnUserReactionForVideo() {
        when(reactionRepository.findByUserIdAndVideoId(userId, videoId)).thenReturn(Optional.of(reaction));

        Reaction result = reactionService.getUserReactionForVideo(userId, videoId);

        assertNotNull(result);
        assertEquals(ReactionType.LIKE, result.getType());
    }

    // ✅ 7. Test: getReactionCountForComment
    @Test
    void shouldReturnReactionCountsForComment() {
        List<Reaction> reactions = List.of(
                Reaction.builder().type(ReactionType.LOVE).build(),
                Reaction.builder().type(ReactionType.LOVE).build(),
                Reaction.builder().type(ReactionType.DISLIKE).build()
        );
        doReturn(List.of(
                new Object[] { ReactionType.LOVE, 2L },
                new Object[] { ReactionType.DISLIKE, 1L }
        )).when(reactionRepository).countReactionByCommentId(any(UUID.class));

        Map<ReactionType, Long> counts = reactionService.getReactionCountForComment(commentId);

        assertEquals(2L, counts.get(ReactionType.LOVE));
        assertEquals(1L, counts.get(ReactionType.DISLIKE));
    }
}
