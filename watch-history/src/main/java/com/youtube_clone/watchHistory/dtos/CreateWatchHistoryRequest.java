package com.youtube_clone.watchHistory.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateWatchHistoryRequest {
    @NotNull(message = "User ID cannot be null")
    private UUID userId;

    @NotNull(message = "Video ID cannot be null")
    private UUID videoId;

    @NotBlank(message = "IP address cannot be blank")
    private String ipAddress;

    @NotNull(message = "Session start time cannot be null")
    private LocalDateTime sessionStartTime;

    @NotNull(message = "Session end time cannot be null")
    private LocalDateTime sessionEndTime;
}
