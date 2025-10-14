package com.youtube_clone.watchHistory.controller;

import com.youtube_clone.watchHistory.dtos.CreateWatchHistoryRequest;
import com.youtube_clone.watchHistory.dtos.WatchHistoryDTO;
import com.youtube_clone.watchHistory.entity.WatchHistory;
import com.youtube_clone.watchHistory.exceptions.ApiResponse;
import com.youtube_clone.watchHistory.mapper.WatchHistoryMapper;
import com.youtube_clone.watchHistory.service.WatchHistoryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/watch-history")
@Tag(name = "Watch History", description = "Track and manage video watch sessions for users.")
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;
    private final Logger logger = LoggerFactory.getLogger(WatchHistoryController.class);

    @PostMapping
    public ResponseEntity<ApiResponse<WatchHistoryDTO>> logWatchSession(
            @Valid @RequestBody CreateWatchHistoryRequest request,
            @Parameter(description = "Video owner's UUID") @RequestParam UUID videoOwnerId
    ) {
        WatchHistory history = watchHistoryService.recordWatch(
                request.getUserId(),
                request.getVideoId(),
                request.getIpAddress(),
                request.getSessionStartTime(),
                request.getSessionEndTime(),
                videoOwnerId
        );

        logger.info("User {} watched video {} for {} seconds",
                request.getUserId(), request.getVideoId(),
                history.getDuration());

        return ResponseEntity.ok(ApiResponse.success(
                "Watch session logged successfully",
                WatchHistoryMapper.toDTO(history)
        ));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<WatchHistoryDTO>>> getUserWatchHistory(
            @Parameter(description = "User ID") @PathVariable UUID userId
    ) {
        List<WatchHistory> historyList = watchHistoryService.getWatchHistoryForUser(userId);
        if (historyList.isEmpty()) {
            logger.warn("No watch history found for user {}", userId);
            return ResponseEntity.ok(ApiResponse.success("No watch history found", List.of()));
        }

        logger.info("Fetched {} watch history records for user {}", historyList.size(), userId);
        return ResponseEntity.ok(ApiResponse.success(
                "Watch history retrieved successfully",
                WatchHistoryMapper.toDTOList(historyList)
        ));
    }

    // -----------------------------------------------------
    // 3️⃣ Get total unique viewers for a video
    // -----------------------------------------------------
    @GetMapping("/video/{videoId}/views")
    public ResponseEntity<ApiResponse<Long>> getUniqueViewers(
            @Parameter(description = "Video ID") @PathVariable UUID videoId
    ) {
        long viewers = watchHistoryService.getUniqueViewers(videoId);
        logger.info("Video {} has {} unique viewers", videoId, viewers);
        return ResponseEntity.ok(ApiResponse.success("Unique viewer count retrieved", viewers));
    }

    // -----------------------------------------------------
    // 4️⃣ Maintenance cleanup
    // -----------------------------------------------------
    @DeleteMapping("/cleanup")
    public ResponseEntity<ApiResponse<Void>> cleanupOldHistory(
            @Parameter(description = "Days threshold (e.g., 30)") @RequestParam int days
    ) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);
        watchHistoryService.cleanupOldEntries(cutoff);
        logger.info("Deleted watch history entries older than {} days", days);
        return ResponseEntity.ok(ApiResponse.success("Old entries cleaned up successfully", null));
    }
}

