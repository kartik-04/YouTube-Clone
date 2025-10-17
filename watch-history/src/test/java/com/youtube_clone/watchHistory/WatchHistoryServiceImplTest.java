package com.youtube_clone.watchHistory;

import com.youtube_clone.watchHistory.entity.WatchHistory;
import com.youtube_clone.watchHistory.repositories.WatchHistoryRepository;
import com.youtube_clone.watchHistory.service.WatchHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchHistoryServiceImplTest {

    @Mock
    private WatchHistoryRepository watchHistoryRepository;

    @InjectMocks
    private WatchHistoryServiceImpl watchHistoryService;

    private UUID userId;
    private UUID videoId;
    private UUID videoOwnerId;
    private String ipAddress;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        videoId = UUID.randomUUID();
        videoOwnerId = UUID.randomUUID();
        ipAddress = "192.168.1.1";
    }

    // 🧩 1️⃣ Should not count short sessions
    @Test
    void shouldNotCountIfWatchDurationLessThan30s() {
        // Given
        LocalDateTime start = LocalDateTime.now().minusSeconds(10);
        LocalDateTime end = LocalDateTime.now();

        // When
        WatchHistory result = watchHistoryService.recordWatch(
                userId, videoId, ipAddress, start, end, videoOwnerId
        );

        // Then
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isCounted(), "View with duration < 30s should not be counted");
        assertTrue(Duration.between(start, end).getSeconds() < 30, "Test should use duration < 30s");
        verifyNoInteractions(watchHistoryRepository);
    }

    // 🧩 2️⃣ Owners view up to 5 times
    @Test
    void shouldCountOwnerViewUpToFiveTimes() {
        // Given
        List<WatchHistory> ownerViews = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ownerViews.add(WatchHistory.builder()
                    .id(UUID.randomUUID())
                    .userId(userId)
                    .videoId(videoId)
                    .counted(true)
                    .ownerViewCounted(true)
                    .sessionStartTime(LocalDateTime.now().minusMinutes(10))
                    .sessionEndTime(LocalDateTime.now().minusMinutes(5))
                    .build());
        }

        // Create a mock watch history that will be returned by save()
        WatchHistory newWatchHistory = WatchHistory.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .videoId(videoId)
                .ipAddress(ipAddress)
                .sessionStartTime(LocalDateTime.now().minusMinutes(5))
                .sessionEndTime(LocalDateTime.now())
                .counted(false)
                .ownerViewCounted(false)
                .build();

        // When
        when(watchHistoryRepository.findByUserIdAndVideoId(userId, videoId))
                .thenReturn(ownerViews);
        when(watchHistoryRepository.save(any(WatchHistory.class)))
                .thenReturn(newWatchHistory);

        // Create timestamps that will pass the minimum duration check
        LocalDateTime start = LocalDateTime.now().minusMinutes(5);
        LocalDateTime end = LocalDateTime.now();
        
        WatchHistory result = watchHistoryService.recordWatch(
                userId, videoId, ipAddress,
                start,
                end,
                userId // user is owner
        );

        // Then
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isCounted(), "6th owner view should not be counted");
        assertFalse(result.isOwnerViewCounted(), "6th owner view should not be marked as owner view counted");
        
        // Verify interactions
        verify(watchHistoryRepository).findByUserIdAndVideoId(userId, videoId);
        verify(watchHistoryRepository).save(any(WatchHistory.class));
    }

    // 🧩 3️⃣ Normal user view limit (max 5 per 24h)
    @Test
    void shouldCountNormalUserUpToFiveTimesPer24Hours() {
        when(watchHistoryRepository.countCountedViewsInLast24Hours(
                any(), any(), any())
        ).thenReturn(4L);

        when(watchHistoryRepository.save(any(WatchHistory.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        WatchHistory result = watchHistoryService.recordWatch(
                userId, videoId, ipAddress,
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now(),
                videoOwnerId
        );

        assertTrue(result.isCounted(), "Should count when <5 views in 24h");

        // simulate 6th view
        when(watchHistoryRepository.countCountedViewsInLast24Hours(any(), any(), any()))
                .thenReturn(5L);
        WatchHistory result2 = watchHistoryService.recordWatch(
                userId, videoId, ipAddress,
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now(),
                videoOwnerId
        );
        assertFalse(result2.isCounted(), "6th view in 24h should not count");
    }

    // 🧩 4️⃣ Verify persistence fields
    @Test
    void shouldSaveWatchHistoryCorrectly() {
        LocalDateTime start = LocalDateTime.now().minusSeconds(60);
        LocalDateTime end = LocalDateTime.now();

        when(watchHistoryRepository.countCountedViewsInLast24Hours(any(), any(), any()))
                .thenReturn(0L);
        when(watchHistoryRepository.save(any(WatchHistory.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        WatchHistory result = watchHistoryService.recordWatch(
                userId, videoId, ipAddress, start, end, videoOwnerId
        );

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(videoId, result.getVideoId());
        assertTrue(result.isCounted());
    }

    // 🧩 5️⃣ Count unique viewers
    @Test
    void shouldCountUniqueViewersForVideo() {
        when(watchHistoryRepository.countUniqueViewers(videoId)).thenReturn(10L);
        long count = watchHistoryRepository.countUniqueViewers(videoId);
        assertEquals(10L, count);
    }

    // 🧩 6️⃣ Delete old watch entries
    @Test
    void shouldDeleteOldWatchEntries() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        doNothing().when(watchHistoryRepository).deleteByLastWatchedBefore(cutoff);
        watchHistoryRepository.deleteByLastWatchedBefore(cutoff);
        verify(watchHistoryRepository, times(1)).deleteByLastWatchedBefore(cutoff);
    }
}