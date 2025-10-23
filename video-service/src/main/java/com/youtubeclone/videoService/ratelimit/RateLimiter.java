package com.youtubeclone.videoService.ratelimit;

import java.util.UUID;

public interface RateLimiter {
    boolean allowRequest(UUID key);             // key may represent creatorId or videoId depending on context.
}