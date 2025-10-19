package com.youtubeclone.videoService.ratelimit;

import java.util.UUID;

public interface RateLimiter {
    boolean allowRequest(UUID videoId);
}
