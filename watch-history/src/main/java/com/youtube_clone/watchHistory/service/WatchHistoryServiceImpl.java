package com.youtube_clone.watchHistory.service;

import com.youtube_clone.watchHistory.entity.WatchHistory;
import com.youtube_clone.watchHistory.repositories.WatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of the WatchHistoryService interface that provides functionality
 * for tracking and managing video watch history. This service handles recording watch sessions,
 * retrieving watch history, and enforcing business rules around view counting.
 * 
 * <p>Key features include:
 * <ul>
 *   <li>Recording watch sessions with duration tracking</li>
 *   <li>Enforcing view counting rules (minimum duration, daily limits)</li>
 *   <li>Managing view counts for video owners</li>
 *   <li>Providing watch history retrieval and analytics</li>
 * </ul>
 * 
 * @see WatchHistoryService
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class WatchHistoryServiceImpl implements WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;

    private static final int MINIMUM_VIEW_DURATION = 30;
    private static final int MAXIMUM_DAILY_VIEWS_PER_USER = 5;


    /**
     * Records a watch session for a user and video, applying business rules for view counting.
     * 
     * @param userId The ID of the user who watched the video
     * @param videoId The ID of the video that was watched
     * @param ipAddress The IP address of the viewer (can be null for authenticated users)
     * @param sessionStartTime When the watch session started
     * @param sessionEndTime When the watch session ended
     * @param videoOwnerId The ID of the video owner (used for special owner view counting)
     * @return The saved WatchHistory record, or null if the view doesn't meet counting criteria
     * @throws IllegalArgumentException if any required parameter is null or if time range is invalid
     * @see #shouldCountAsView(LocalDateTime, LocalDateTime)
     */
    @Override
    public WatchHistory recordWatch(UUID userId, UUID videoId, String ipAddress,
                                  LocalDateTime sessionStartTime, LocalDateTime sessionEndTime,
                                  UUID videoOwnerId) {

        boolean counted = false;
        boolean ownerViewCounted = false;

        WatchHistory watchHistory = WatchHistory.builder()
                .userId(userId)
                .videoId(videoId)
                .ipAddress(ipAddress)
                .sessionStartTime(sessionStartTime)
                .sessionEndTime(sessionEndTime)
                .duration(Duration.between(sessionStartTime, sessionEndTime).getSeconds())
                .lastWatched(LocalDateTime.now())
                .build();
        // Check minimum duration
        if (!shouldCountAsView(sessionStartTime, sessionEndTime)) {
            log.info("View duration is less than 30 seconds");
            watchHistory.setCounted(false);
            return null; // Ignore under-30s views
        }

        if (userId.equals(videoOwnerId)) {
            List<WatchHistory> ownerList = watchHistoryRepository
                    .findByUserIdAndVideoId(userId, videoId);
            long ownerCountedViews = ownerList.stream()
                    .filter(WatchHistory::isCounted)
                    .count();
            if (ownerCountedViews < 5) {
                counted = true;
                ownerViewCounted = true;
            }
        }else{
            LocalDateTime last24h = LocalDateTime.now().minusHours(24);
            long countedViewIn24h = watchHistoryRepository.countViewsInLast24Hours(userId, videoId, last24h);
            if (countedViewIn24h < MAXIMUM_DAILY_VIEWS_PER_USER){
                counted = true;
            }
        }

        watchHistory.setCounted(counted);
        watchHistory.setOwnerViewCounted(ownerViewCounted);
        // count existing valid view in 24 hours

        return watchHistoryRepository.save(watchHistory);
    }

    /**
     * Retrieves the complete watch history for a specific user, ordered by the most recent first.
     * 
     * @param userId The ID of the user whose history to retrieve
     * @return A list of WatchHistory records, ordered by lastWatched in descending order
     * @throws IllegalArgumentException if userId is null
     */
    @Override
    public List<WatchHistory> getWatchHistoryForUser(UUID userId) {
        return watchHistoryRepository.findByUserIdOrderByLastWatchedDesc(userId);
    }

    /**
     * Retrieves all watch history records for a specific user and video combination.
     * 
     * @param userId The ID of the user
     * @param videoId The ID of the video
     * @return A list of WatchHistory records for the specified user and video
     * @throws IllegalArgumentException if either userId or videoId is null
     */
    @Override
    public List<WatchHistory> getRecentWatchHistory(UUID userId, UUID videoId) {
        return watchHistoryRepository.findByUserIdAndVideoId(userId, videoId);
    }

    /**
     * Counts how many valid views a user has for a specific video in the last 24 hours.
     * A view is considered valid if it meets the minimum duration requirement.
     * 
     * @param userId The ID of the user
     * @param videoId The ID of the video
     * @return The count of valid views in the last 24 hours
     * @throws IllegalArgumentException if either userId or videoId is null
     */
    @Override
    public long getValidViewInLast24Hours(UUID userId, UUID videoId) {
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        return watchHistoryRepository.countViewsInLast24Hours(userId, videoId, since);
    }

    /**
     * Counts the number of unique viewers for a specific video.
     * 
     * @param videoId The ID of the video
     * @return The count of unique viewers for the specified video
     * @throws IllegalArgumentException if videoId is null
     */
    @Override
    public long getUniqueViewers(UUID videoId) {
        return watchHistoryRepository.countUniqueViewers(videoId);
    }

    /**
     * Removes watch history entries older than the specified cutoff date.
     * This is typically used for data retention policies or cleanup of old records.
     * 
     * @param cutoff The cutoff date; entries older than this will be deleted
     * @throws IllegalArgumentException if cutoff is null
     */
    @Override
    public void cleanupOldEntries(LocalDateTime cutoff) {
        watchHistoryRepository.deleteByLastWatchedBefore(cutoff);
    }


    /**
     * Determines if a watch session should be counted as a valid view based on its duration.
     * Currently, a view is valid if it lasts at least 30 seconds.
     * 
     * @param sessionStart When the session started (must not be null)
     * @param sessionEnd When the session ended (must not be null and must be after sessionStart)
     * @return true if the session should be counted as a view, false otherwise
     * @throws IllegalArgumentException if either time parameter is null or if ends is before start
     */
    @Override
    public boolean shouldCountAsView(LocalDateTime sessionStart, LocalDateTime sessionEnd) {
        return Duration.between(sessionStart, sessionEnd).getSeconds() >= MINIMUM_VIEW_DURATION;
    }
}
