package com.youtubeclone.videoService.ratelimit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FixedWindowRateLimiter implements RateLimiter {
    private final int limit;
    private final long windowMillis;
    private final Map<UUID, UserBucket> buckets = new ConcurrentHashMap<>();

    public FixedWindowRateLimiter(
            @Value("${rate.limit.requests:10}") int limit,
            @Value("${rate.limit.window.millis:60000}") long windowMillis
    ) {
        this.limit = limit;
        this.windowMillis = windowMillis;
    }

    /**
     * @param videoId Unique qualifier for video
     */
    @Override
    public boolean allowRequest(UUID videoId) {
        long now = Instant.now().toEpochMilli();
        buckets.putIfAbsent(videoId, new UserBucket());
        UserBucket bucket = buckets.get(videoId);
        if(now - bucket.windowStart >= windowMillis){
            bucket.windowStart = now;
            bucket.count = 0;
        }
        if(bucket.count < limit){
            bucket.count++;
            return false;
        }
        return true;
    }

    private static class UserBucket{
        int count;
        long windowStart;
    }
}
