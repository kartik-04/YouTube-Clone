package com.youtubeclone.security.ratelimit;

import java.util.UUID;

public interface RateLimiter {
    boolean allowRequest(UUID videoId);
}
