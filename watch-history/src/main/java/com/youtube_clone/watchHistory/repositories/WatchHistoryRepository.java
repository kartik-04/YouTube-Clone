package com.youtube_clone.watchHistory.repositories;

import com.youtube_clone.watchHistory.entity.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, UUID> {
    List<WatchHistory> findByUserIdOrderByLastWatchedDesc(UUID userId);

    Optional<WatchHistory> findByUserIdAndVideoId(UUID userId, UUID videoId);

    @Query("SELECT COUNT(w) FROM WatchHistory w WHERE w.userId = :userId " +
            "AND w.videoId = :videoId AND w.lastWatched >= :since")
    long countViewsInLast24Hours(UUID userId, UUID videoId, LocalDateTime since);

    @Query("SELECT COUNT(DISTINCT w.userId) FROM WatchHistory w " +
            "WHERE w.videoId = :videoId")
    long countUniqueViewers(UUID videoId);

    void deleteByLastWatchedBefore(LocalDateTime cutoff);

    @Query("SELECT COUNT(w) FROM WatchHistory w WHERE w.videoId = :videoId")
    long countTotalViews(UUID videoId);

    boolean existsByUserIdAndVideoId(UUID userId, UUID videoId);
}
