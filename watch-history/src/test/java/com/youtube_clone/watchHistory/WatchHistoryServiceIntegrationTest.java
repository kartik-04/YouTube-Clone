package com.youtube_clone.watchHistory;

import com.youtube_clone.watchHistory.entity.WatchHistory;
import com.youtube_clone.watchHistory.repositories.WatchHistoryRepository;
import com.youtube_clone.watchHistory.service.WatchHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class WatchHistoryServiceIntegrationTest {

    @Autowired
    private WatchHistoryService watchHistoryService;

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    @Test
    void shouldRecordWatchHistory() {
        UUID userId = UUID.randomUUID();
        UUID videoId = UUID.randomUUID();
        UUID videoOwnerId = UUID.randomUUID();
        String ipAddress = "192.168.1.100";

        LocalDateTime start = LocalDateTime.now().minusMinutes(2);
        LocalDateTime end = LocalDateTime.now();

        WatchHistory saved = watchHistoryService.recordWatch(
                userId,
                videoId,
                ipAddress,
                start,
                end,
                videoOwnerId
        );
        assertNotNull(saved.getId());
        assertEquals(userId, saved.getUserId());
        assertEquals(videoId, saved.getVideoId());
        assertTrue(saved.isCounted(), "Should be counted since duration > 30s");

        System.out.println("✅ Watch session saved: " + saved);
    }

    @Test
    void shouldGetWatchHistoryForUser() {
        UUID userId = UUID.randomUUID();
        for(int i = 0; i < 6; i++){
            UUID videoId = UUID.randomUUID();
            UUID videoOwnerId = UUID.randomUUID();
            String ipAddress = "192.168.1.100";

            LocalDateTime start = LocalDateTime.now().minusMinutes(2);
            LocalDateTime end = LocalDateTime.now();

            WatchHistory saved = watchHistoryService.recordWatch(
                    userId,
                    videoId,
                    ipAddress,
                    start,
                    end,
                    videoOwnerId
            );
        }
        List<WatchHistory> history = watchHistoryService.getWatchHistoryForUser(userId);
        assertEquals(6, history.size());
        assertFalse(history.isEmpty());
        assertTrue(history.getFirst().isCounted());
    }

    @Test
    void shouldGetWatchHistoryForSpecificVideo(){
        UUID userId = UUID.randomUUID();
        UUID videoId = UUID.randomUUID();
        for(int i = 0; i < 6; i++){
            UUID videoOwnerId = UUID.randomUUID();
            String ipAddress = "192.168.1.100";

            LocalDateTime start = LocalDateTime.now().minusMinutes(2);
            LocalDateTime end = LocalDateTime.now();

            WatchHistory saved = watchHistoryService.recordWatch(
                    userId,
                    videoId,
                    ipAddress,
                    start,
                    end,
                    videoOwnerId
            );
        }
        List<WatchHistory> history = watchHistoryService.getRecentWatchHistory(userId,videoId);
        assertEquals(6, history.size());
        assertFalse(history.isEmpty());
        assertTrue(history.getFirst().isCounted());
    }

    @Test
    void shouldGetValidViewInLast24Hours() {
        UUID userId = UUID.randomUUID();
        UUID videoId = UUID.randomUUID();

        for (int i = 0; i < 7; i++) {
            watchHistoryService.recordWatch(
                    userId, videoId, "192.168.1.100",
                    LocalDateTime.now().minusMinutes(2),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }

        long count = watchHistoryService.getValidViewInLast24Hours(userId, videoId);

        // ⚡ Fix: business rule says only up to 5 views count per 24h
        assertTrue(count <= 5, "At most 5 views should be counted within 24 hours");
        System.out.println("🎯 Counted views (max 5): " + count);
    }

    @Test
    void shouldCountUniqueViewersForVideo(){
        UUID videoId = UUID.randomUUID();
        for(int i = 0; i < 6; i++){
            UUID userId = UUID.randomUUID();
            UUID videoOwnerId = UUID.randomUUID();
            String ipAddress = "192.168.1.100";

            LocalDateTime start = LocalDateTime.now().minusMinutes(2);
            LocalDateTime end = LocalDateTime.now();

            WatchHistory saved = watchHistoryService.recordWatch(
                    userId,
                    videoId,
                    ipAddress,
                    start,
                    end,
                    videoOwnerId
            );
        }
        long count = watchHistoryService.getUniqueViewers(videoId);
        assertEquals(6, count);
    }

    @Test
    void shouldDeleteByLastWatchBefore() {
        UUID userId = UUID.randomUUID();
        UUID videoId = UUID.randomUUID();

        for (int i = 0; i < 6; i++) {
            watchHistoryService.recordWatch(
                    userId, videoId, "192.168.1.100",
                    LocalDateTime.now().minusMinutes(10), // ⚡ Older sessions
                    LocalDateTime.now().minusMinutes(5),
                    UUID.randomUUID()
            );
        }

        // ⚡ Fix: cutoff must be AFTER session times to delete
        watchHistoryService.cleanupOldEntries(LocalDateTime.now().minusMinutes(1));

        assertEquals(0, watchHistoryRepository.count(), "All old records should be deleted");
        assertTrue(watchHistoryRepository.findAll().isEmpty());
    }

    @Test
    void shouldGetTotalView(){
        UUID videoId = UUID.randomUUID();
        for(int i = 0; i < 10; i++){
            UUID userId = UUID.randomUUID();
            UUID videoOwnerId = UUID.randomUUID();
            String ipAddress = "192.168.1.100";

            LocalDateTime start = LocalDateTime.now().minusSeconds(55);
            LocalDateTime end = LocalDateTime.now();

            WatchHistory saved = watchHistoryService.recordWatch(
                    userId,
                    videoId,
                    ipAddress,
                    start,
                    end,
                    videoOwnerId
            );
        }
        long count = watchHistoryRepository.countTotalViews(videoId);
        assertEquals(10, count);
    }
}
