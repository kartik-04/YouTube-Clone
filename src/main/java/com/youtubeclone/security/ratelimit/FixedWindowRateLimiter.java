package com.youtubeclone.security.ratelimit;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FixedWindowRateLimiter implements RateLimiter {
    private final int limit;
    private final long windowMillis;
    private final Map<UUID, UserBucket> buckets = new HashMap<>();

    public FixedWindowRateLimiter(int limit, long windowMillis) {
        this.limit = limit;
        this.windowMillis = windowMillis;
    }

    /**
     * @param videoId
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
            return true;
        }
        return false;
    }

    private static class UserBucket{
        int count;
        long windowStart;
    }
}
