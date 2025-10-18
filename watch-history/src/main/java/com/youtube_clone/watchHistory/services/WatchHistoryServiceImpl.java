package com.youtube_clone.watchHistory.services;

import com.youtube_clone.watchHistory.entities.WatchHistory;
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
        log.info("Recording watch session - User: {}, Video: {}, Start: {}, End: {}", 
                userId, videoId, sessionStartTime, sessionEndTime);
                
        if (userId == null || videoId == null || sessionStartTime == null || sessionEndTime == null) {
            log.error("Invalid parameters - userId: {}, videoId: {}, sessionStartTime: {}, sessionEndTime: {}", 
                    userId, videoId, sessionStartTime, sessionEndTime);
            throw new IllegalArgumentException("Required parameters cannot be null");
        }

        boolean counted = false;
        boolean ownerViewCounted = false;

        WatchHistory watchHistory = WatchHistory.builder()
                .userId(userId)
                .videoId(videoId)
                .ipAddress(ipAddress)
                .sessionStartTime(sessionStartTime)
                .sessionEndTime(sessionEndTime)
                .duration(Duration.between(sessionStartTime, sessionEndTime).getSeconds())
                .lastWatched(sessionEndTime)
                .viewDate(sessionStartTime.toLocalDate())
                .build();
        // Check minimum duration
        if (!shouldCountAsView(sessionStartTime, sessionEndTime)) {
            long duration = Duration.between(sessionStartTime, sessionEndTime).getSeconds();
            log.info("View duration of {} seconds is less than minimum required {} seconds. View not counted.", 
                    duration, MINIMUM_VIEW_DURATION);
            watchHistory.setCounted(false);
            return watchHistory;
        }

        if (userId.equals(videoOwnerId)) {
            log.debug("Processing view from video owner: {}", userId);
            List<WatchHistory> ownerList = watchHistoryRepository
                    .findByUserIdAndVideoId(userId, videoId);
            long ownerCountedViews = ownerList.stream()
                    .filter(WatchHistory::isCounted)
                    .count();
            if (ownerCountedViews < 5) {
                log.debug("Owner view counted. Current owner view count: {}", ownerCountedViews + 1);
                counted = true;
                ownerViewCounted = true;
            } else {
                log.info("Maximum owner view count (5) reached for user: {}, video: {}", userId, videoId);
            }
        } else {
            LocalDateTime last24h = LocalDateTime.now().minusHours(24);
            long countedViewIn24h = watchHistoryRepository.countCountedViewsInLast24Hours(userId, videoId, last24h);
            log.debug("User {} has {} counted views for video {} in last 24 hours", 
                    userId, countedViewIn24h, videoId);

            if (countedViewIn24h >= MAXIMUM_DAILY_VIEWS_PER_USER) {
                log.info("Daily view limit of {} reached for user: {}, video: {}",
                        MAXIMUM_DAILY_VIEWS_PER_USER, userId, videoId);
            } else {
                counted = true;
            }
        }

        watchHistory.setCounted(counted);
        watchHistory.setOwnerViewCounted(ownerViewCounted);
        // count existing valid view in 24 hours

        WatchHistory savedHistory = watchHistoryRepository.save(watchHistory);
        log.debug("Successfully saved watch history record with ID: {}", savedHistory.getId());
        return savedHistory;
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
        log.debug("Fetching watch history for user: {}", userId);
        List<WatchHistory> history = watchHistoryRepository.findByUserIdOrderByLastWatchedDesc(userId);
        log.debug("Found {} watch history records for user: {}", history.size(), userId);
        return history;
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
        log.debug("Fetching watch history for user: {} and video: {}", userId, videoId);
        List<WatchHistory> history = watchHistoryRepository.findByUserIdAndVideoId(userId, videoId);
        log.debug("Found {} watch history records for user: {} and video: {}", 
                history.size(), userId, videoId);
        return history;
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
        log.debug("Counting valid views in last 24 hours for user: {}, video: {}", userId, videoId);
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        long count = watchHistoryRepository.countCountedViewsInLast24Hours(userId, videoId, since);
        log.debug("Found {} valid views in last 24 hours for user: {}, video: {}", 
                count, userId, videoId);
        return count;
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
        log.debug("Counting unique viewers for video: {}", videoId);
        long count = watchHistoryRepository.countUniqueViewers(videoId);
        log.info("Found {} unique viewers for video: {}", count, videoId);
        return count;
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
        log.info("Initiating cleanup of watch history entries older than: {}", cutoff);
        try {
            long countBefore = watchHistoryRepository.count();
            watchHistoryRepository.deleteByLastWatchedBefore(cutoff);
            long countAfter = watchHistoryRepository.count();
            long deletedCount = countBefore - countAfter;
            log.info("Cleanup completed. Removed {} old watch history entries", deletedCount);
        } catch (Exception e) {
            log.error("Error during cleanup of watch history entries older than {}: {}", 
                    cutoff, e.getMessage(), e);
            throw e;
        }
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
        if (sessionStart == null || sessionEnd == null) {
            log.warn("Invalid session times - start: {}, end: {}", sessionStart, sessionEnd);
            return false;
        }
        
        long duration = Duration.between(sessionStart, sessionEnd).getSeconds();
        boolean isValid = duration >= MINIMUM_VIEW_DURATION;
        
        if (!isValid) {
            log.debug("View duration of {} seconds is below minimum threshold of {} seconds", 
                    duration, MINIMUM_VIEW_DURATION);
        }
        
        return isValid;
    }
}
