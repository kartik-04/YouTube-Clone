package com.youtube_clone.watchHistory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "watch_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID videoId;

    @Column(nullable = false)
    private LocalDateTime sessionStartTime;

    @Column(nullable = false)
    private LocalDateTime sessionEndTime;

    @Column(nullable = false)
    private Long watchDuration; // seconds

    @Column(nullable = false)
    private boolean counted; // if true -> contributes to view count

    @Column(nullable = false)
    private LocalDate viewDate; // for enforcing daily limits

    private String ipAddress; // viewer IP (optional may be null for authenticated users)

    @Column(nullable = false)
    private boolean ownerViewCounted; // true if it’s owner’s counted lifetime view

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateWatchDuration() {
        if (sessionStartTime != null && sessionEndTime != null) {
            this.watchDuration = java.time.Duration.between(sessionStartTime, sessionEndTime).getSeconds();
        }
    }
}
