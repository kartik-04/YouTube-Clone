package com.youtube_clone.watchHistory.config;

import com.youtube_clone.watchHistory.entities.WatchHistory;
import com.youtube_clone.watchHistory.repositories.WatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final WatchHistoryRepository watchHistoryRepository;
    private final Random random = new Random();

    @Override
    public void run(String... args){
        if(watchHistoryRepository.count() > 5){
            System.out.println("✅ WatchHistory table already seeded. Skipping...");
            return;
        }
        System.out.println("\uD83D\uDE80 Seeding WatchHistory table with Realistic data....");

        List<UUID> users = IntStream.range(0, 100)
                .mapToObj(i -> UUID.randomUUID())
                .toList();

        List<UUID> videos = IntStream.range(0, 5)
                .mapToObj(i -> UUID.randomUUID())
                .toList();

        Map<UUID, UUID> videoOwner = new HashMap<>();
        for(UUID video : videos){
            videoOwner.put(video, users.get(random.nextInt(users.size())));
        }

        List<WatchHistory> entries = new ArrayList<>();

        for(UUID userId : users){
            for(UUID videoId : videos){
                UUID ownerId = videoOwner.get(videoId);

                // Each user watches each video for 1-7 times
                int watchCount = 1 + random.nextInt(7);
                for(int i = 0; i < watchCount; i++){
                    LocalDateTime start = LocalDateTime.now().minusHours(random.nextInt(48)).minusMinutes(random.nextInt(60));
                    LocalDateTime end = start.plusSeconds(random.nextInt(400) + 30);
                    boolean isOwner = userId.equals(ownerId);

                    boolean counted;
                    boolean ownerCounted = false;

                    if(isOwner){
                        // Owner view only counts up to 5 times for lifetime
                        long previousOwnerViews = entries.stream()
                                .filter(e -> e.getUserId().equals(userId)
                                        && e.getVideoId().equals(videoId)
                                        && e.isOwnerViewCounted())
                                .count();
                        counted = previousOwnerViews < 5;
                        ownerCounted = counted;
                    }else{
                        // Non-owner view counts up to 5 times per day
                        long viewIn24h = entries.stream()
                                .filter(e -> e.getUserId().equals(userId)
                                        && e.getVideoId().equals(videoId)
                                        && e.isCounted()
                                        && e.getLastWatched().isAfter(LocalDateTime.now().minusHours(24)))
                                .count();
                        counted = viewIn24h < 5;
                    }

                    WatchHistory record = WatchHistory.builder()
                            .userId(userId)
                            .videoId(videoId)
                            .ipAddress("127.0.0." + random.nextInt(256))
                            .sessionStartTime(start)
                            .sessionEndTime(end)
                            .duration(Duration.between(start, end).getSeconds())
                            .lastWatched(end)
                            .viewDate(LocalDateTime.now().toLocalDate())
                            .counted(counted)
                            .ownerViewCounted(ownerCounted)
                            .build();

                    entries.add(record);
                }
            }
        }
        watchHistoryRepository.saveAll(entries);
        System.out.printf("✅ Seeded %d WatchHistory records for %d users and %d videos.%n",
                entries.size(), users.size(), videos.size());
    }
}
