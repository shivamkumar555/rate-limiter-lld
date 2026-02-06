package RatelimiterImplementation;

public interface RateLimiter {
    boolean allowRequest(String clientId);
}
