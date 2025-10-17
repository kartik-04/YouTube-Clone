package com.youtube_clone.reaction.integration;

import com.youtube_clone.reaction.entities.Reaction;
import com.youtube_clone.reaction.entities.ReactionType;
import com.youtube_clone.reaction.repositories.ReactionRepository;
import com.youtube_clone.reaction.services.ReactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class ReactionServiceImplTest {

    @Autowired
    private ReactionServiceImpl reactionService;

    @Autowired
    private ReactionRepository reactionRepository;

    @Test
    void shouldCreateAndDeactivateReactionForAVideo() {
        UUID videoId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        // Create reaction
        Reaction saved = reactionService.reactToVideo(userId, videoId, ReactionType.LOVE);

        assertNotNull(saved);
        assertEquals(ReactionType.LOVE, saved.getType());
        assertTrue(saved.isActive());

        System.out.println("✅ Comment reaction added successfully: " + saved.getId());

        // Deactivate reaction
        reactionService.removeReactionFromVideo(userId, videoId);
        saved = reactionRepository.findByUserIdAndVideoId(userId, videoId).orElseThrow();
        assertFalse(saved.isActive());
        System.out.println(" Reaction deactivated successfully: " + saved.getId());
        System.out.println(" Reaction false for the ReactionType " + saved.getType());

        // Clean up
        reactionRepository.delete(saved);
    }

    @Test
    void shouldCreateAndDeactivateReactionForComment() {
        UUID commentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        // Create reaction
        Reaction saved = reactionService.reactToComment(userId, commentId, ReactionType.LOVE);

        assertNotNull(saved);
        assertEquals(ReactionType.LOVE, saved.getType());
        assertTrue(saved.isActive());

        System.out.println("✅ Comment reaction added successfully: " + saved.getId());

        // Deactivate reaction
        reactionService.removeReactionFromComment(userId, commentId);
        Reaction updated = reactionRepository.findByUserIdAndCommentId(userId, commentId)
                .orElseThrow();
        assertFalse(updated.isActive());
        System.out.println(" Reaction deactivated successfully: " + updated.getId());
        System.out.println(" Reaction false for the ReactionType " + updated.getType());

        // Clean up
        reactionRepository.delete(saved);
    }

    @Test
    void shouldGetReactionCountForVideo() {
        UUID videoId = UUID.randomUUID();
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        UUID user3 = UUID.randomUUID();

        // Create reactions
        reactionService.reactToVideo(user1, videoId, ReactionType.LOVE);
        reactionService.reactToVideo(user2, videoId, ReactionType.LOVE);
        reactionService.reactToVideo(user3, videoId, ReactionType.DISLIKE);

        // Get reaction counts
        Map<ReactionType, Long> counts = reactionService.getReactionCountForVideo(videoId);

        assertEquals(2L, counts.get(ReactionType.LOVE));
        assertEquals(1L, counts.get(ReactionType.DISLIKE));

    }

    @Test
    void shouldGetReactionCountForComment(){
        UUID commentId = UUID.randomUUID();
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        UUID user3 = UUID.randomUUID();

        reactionService.reactToComment(user1,commentId,ReactionType.LOVE);
        reactionService.reactToComment(user2,commentId,ReactionType.LOVE);
        reactionService.reactToComment(user3,commentId,ReactionType.DISLIKE);

        Map<ReactionType,Long> counts = reactionService.getReactionCountForComment(commentId);

        assertEquals(2L,counts.get(ReactionType.LOVE));
        assertEquals(1L,counts.get(ReactionType.DISLIKE));
    }

    @Test
    void shouldGetUsersReactionForVideo(){
        UUID userId = UUID.randomUUID();
        UUID videoId = UUID.randomUUID();

        reactionService.reactToVideo(userId,videoId,ReactionType.LOVE);
        Reaction reaction = reactionRepository.findByUserIdAndVideoId(userId, videoId).orElseThrow();
        assertNotNull(reaction);
        assertEquals(userId,reaction.getUserId());
        assertEquals(videoId,reaction.getVideoId());

        assertEquals(ReactionType.LOVE,reaction.getType());
        assertTrue(reaction.isActive());
    }

    @Test
    void shouldGetUsersReactionForComment(){
        UUID userId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();

        reactionService.reactToComment(userId,commentId,ReactionType.LOVE);
        Reaction reaction = reactionRepository.findByUserIdAndCommentId(userId, commentId).orElseThrow();
        assertNotNull(reaction);
        assertEquals(userId,reaction.getUserId());
        assertEquals(commentId,reaction.getCommentId());

        assertEquals(ReactionType.LOVE,reaction.getType());
        assertTrue(reaction.isActive());
    }

    @Test
    void shouldGetReactionForVideoInPages() {
        // Given
        UUID videoId = UUID.randomUUID();
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        UUID user3 = UUID.randomUUID();
        UUID user4 = UUID.randomUUID();
        UUID user5 = UUID.randomUUID();

        // Create different reactions
        reactionService.reactToVideo(user1, videoId, ReactionType.LIKE);
        reactionService.reactToVideo(user2, videoId, ReactionType.LIKE);
        reactionService.reactToVideo(user3, videoId, ReactionType.DISLIKE);
        reactionService.reactToVideo(user4, videoId, ReactionType.LOVE);
        reactionService.reactToVideo(user5, videoId, ReactionType.LIKE);

        // When - get first page with 2 items
        var page1 = reactionService.getReactionForVideo(videoId, Pageable.ofSize(2));

        // Then
        assertEquals(2, page1.getContent().size(), "First page should have 2 items");
        assertEquals(3, page1.getTotalPages(), "Should have 3 total pages for 5 items with page size 2");
        assertEquals(5, page1.getTotalElements(), "Should have 5 total reactions");

        // When - get second page (page index 1 for 0-based indexing)
        var page2 = reactionService.getReactionForVideo(videoId, Pageable.ofSize(2).withPage(1));

        // Then
        assertEquals(2, page2.getContent().size(), "Second page should have 2 items");
        assertEquals(3, page2.getTotalPages(), "Should still have 3 total pages");
        assertEquals(5, page2.getTotalElements(), "Should still have 5 total reactions");

        // When - get third page (page index 2 for 0-based indexing) with the same page size
        var page3 = reactionService.getReactionForVideo(videoId, Pageable.ofSize(2).withPage(2));

        // Then
        assertEquals(1, page3.getContent().size(), "Last page should have 1 item");
        assertEquals(3, page3.getTotalPages(), "Should still have 3 total pages");
        assertEquals(5, page3.getTotalElements(), "Should still have 5 total reactions");

        // Verify pagination metadata
        assertFalse(page1.isLast(), "First page should not be the last");
        assertFalse(page2.isLast(), "Second page should not be the last");
        assertTrue(page3.isLast(), "Third page should be the last");

        // Verify content
        Set<UUID> allUserIds = Stream.of(
                        page1.getContent(),
                        page2.getContent(),
                        page3.getContent()
                )
                .flatMap(List::stream)
                .map(Reaction::getUserId)
                .collect(Collectors.toSet());

        assertEquals(5, allUserIds.size(), "Should have reactions from all 5 users");
        assertTrue(allUserIds.containsAll(Set.of(user1, user2, user3, user4, user5)),
                "Should contain all user reactions");

        // Verify empty page when out of bounds
        var pageable = PageRequest.of(100, 10); // 100th page, 10 per page
        var emptyPage = reactionService.getReactionForVideo(videoId, pageable);
        assertTrue(emptyPage.isEmpty(), "Should return empty page for out-of-bounds page number");
    }

    @Test
    void shouldGetReactionForCommentInPages(){
        UUID commentId = UUID.randomUUID();
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        UUID user3 = UUID.randomUUID();
        UUID user4 = UUID.randomUUID();
        UUID user5 = UUID.randomUUID();

        reactionService.reactToComment(user1,commentId,ReactionType.LOVE);
        reactionService.reactToComment(user2,commentId,ReactionType.LOVE);
        reactionService.reactToComment(user3,commentId,ReactionType.DISLIKE);
        reactionService.reactToComment(user4,commentId,ReactionType.LOVE);
        reactionService.reactToComment(user5,commentId,ReactionType.LIKE);

        // When - get first page with 2 items
        var page1 = reactionService.getReactionForComment(commentId, Pageable.ofSize(2));

        // Then
        assertEquals(2, page1.getContent().size(), "First page should have 2 items");
        assertEquals(3, page1.getTotalPages(), "Should have 3 total pages for 5 items with page size 2");
        assertEquals(5, page1.getTotalElements(), "Should have 5 total reactions");

        // When - get second page (page index 1 for 0-based indexing)
        var page2 = reactionService.getReactionForComment(commentId, Pageable.ofSize(2).withPage(1));

        // Then
        assertEquals(2, page2.getContent().size(), "Second page should have 2 items");
        assertEquals(3, page2.getTotalPages(), "Should still have 3 total pages");
        assertEquals(5, page2.getTotalElements(), "Should still have 5 total reactions");

        // When - get third page (page index 2 for 0-based indexing) with the same page size
        var page3 = reactionService.getReactionForComment(commentId, Pageable.ofSize(2).withPage(2));

        // Then
        assertEquals(1, page3.getContent().size(), "Last page should have 1 item");
        assertEquals(3, page3.getTotalPages(), "Should still have 3 total pages");
        assertEquals(5, page3.getTotalElements(), "Should still have 5 total reactions");

        // Verify pagination metadata
        assertFalse(page1.isLast(), "First page should not be the last");
        assertFalse(page2.isLast(), "Second page should not be the last");
        assertTrue(page3.isLast(), "Third page should be the last");

        // Verify content
        Set<UUID> allUserIds = Stream.of(
                        page1.getContent(),
                        page2.getContent(),
                        page3.getContent()
                )
                .flatMap(List::stream)
                .map(Reaction::getUserId)
                .collect(Collectors.toSet());

        assertEquals(5, allUserIds.size(), "Should have reactions from all 5 users");
        assertTrue(allUserIds.containsAll(Set.of(user1, user2, user3, user4, user5)),
                "Should contain all user reactions");

        // Verify empty page when out of bounds
        var pageable = PageRequest.of(100, 10); // 100th page, 10 per page
        var emptyPage = reactionService.getReactionForVideo(commentId, pageable);
        assertTrue(emptyPage.isEmpty(), "Should return empty page for out-of-bounds page number");
    }
}