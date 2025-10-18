package com.youtube_clone.watchHistory.services;

import com.youtube_clone.watchHistory.entities.WatchHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface WatchHistoryService {
    WatchHistory recordWatch(UUID userId, UUID videoId, String ipAddress,
                             LocalDateTime sessionStartTime, LocalDateTime sessionEndTime,
                             UUID videoOwnerId);
    List<WatchHistory> getWatchHistoryForUser(UUID userId);
    List<WatchHistory> getRecentWatchHistory(UUID userId, UUID videoId);
    long getValidViewInLast24Hours(UUID userId, UUID videoId);
    long getUniqueViewers(UUID videoId);
    void cleanupOldEntries(LocalDateTime cutoff);
    boolean shouldCountAsView(LocalDateTime sessionStart, LocalDateTime sessionEnd);
}
