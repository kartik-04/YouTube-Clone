package com.youtube_clone.watchHistory.controllers;

import com.youtube_clone.watchHistory.dtos.CreateWatchHistoryRequest;
import com.youtube_clone.watchHistory.dtos.WatchHistoryDTO;
import com.youtube_clone.watchHistory.entities.WatchHistory;
import com.youtube_clone.watchHistory.exceptions.ApiResponse;
import com.youtube_clone.watchHistory.mappers.WatchHistoryMapper;
import com.youtube_clone.watchHistory.services.WatchHistoryService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Record a watch session",
            description = "Logs a user's video watch activity. Only counts the view if duration ≥ 30s. "
                    + "For video owners, only 5 lifetime views are counted."
    )
    @PostMapping("/record")
    public ResponseEntity<ApiResponse<WatchHistoryDTO>> recordWatchHistory(
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

    @Operation(
            summary = "Gives the List of WatchHistory for UserId",
            description = "Takes int the userId and then provides the detailed watch-history for the user."
    )
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
    @Operation(
            summary = "Gives out the unique viewer count for a video",
            description = "Takes in the videoId and then provides the unique viewer count for the video."
    )
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
    @Operation(
            summary = "Deletes watch history entries older than the specified number of days.",
            description = "Deletes watch history entries older than the specified number of days."
    )
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

