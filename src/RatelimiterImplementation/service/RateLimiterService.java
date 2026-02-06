package RatelimiterImplementation.service;

import RatelimiterImplementation.RateLimiter;
import RatelimiterImplementation.RateLimiterFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiterService {
    private final Map<String, RateLimiter> userRateLimiters = new ConcurrentHashMap<>();

    public void registerUser(String userId, String algorithm, int maxRequests, long windowSizeSeconds) {
        userRateLimiters.put(userId, RateLimiterFactory.createRateLimiter(algorithm, maxRequests, windowSizeSeconds * 1000));
    }

    public boolean allowRequest(String userId) {
        RateLimiter rateLimiter = userRateLimiters.get(userId);
        if (rateLimiter == null) throw new IllegalArgumentException("User not registered");
        return rateLimiter.allowRequest(userId);
    }
}
