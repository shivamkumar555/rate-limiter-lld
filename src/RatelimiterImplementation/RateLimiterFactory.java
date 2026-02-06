package RatelimiterImplementation;

public class RateLimiterFactory {
    public static RateLimiter createRateLimiter(String type, int maxRequests, long windowSizeMillis) {
        return switch (type) {
            case "fixed" -> new FixedWindowRateLimiter(maxRequests, windowSizeMillis);
            case "sliding" -> new SlidingWindowRateLimiter(maxRequests, windowSizeMillis);
            case "token-bucket" ->
                    new TokenBucketRateLimiter(maxRequests, (int) (1.0 * maxRequests / windowSizeMillis * 1000));
            case "leaky-bucket" ->
                    new LeakyBucketRateLimiter(maxRequests, (int) (1.0 * maxRequests / windowSizeMillis * 1000));
            default -> throw new IllegalArgumentException("Unsupported type.");
        };
    }
}
