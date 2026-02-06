package RatelimiterImplementation;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;

public class TokenBucketRateLimiter implements RateLimiter{

    int bucketSize;
    int refillRate;
    Map<String, Integer> clientAndAllowedRequest;
    Map<String, Long> clientLastRefillTime;

    public TokenBucketRateLimiter(int bucketSize, int refillRate) {
        this.bucketSize = bucketSize;
        this.refillRate = refillRate;
        this.clientAndAllowedRequest = new HashMap<>();
        this.clientLastRefillTime = new HashMap<>();
    }

    @Override
    public boolean allowRequest(String clientId) {

        Long currTime = System.currentTimeMillis();

        //onboarding the user
        clientAndAllowedRequest.putIfAbsent(clientId, bucketSize);
        clientLastRefillTime.putIfAbsent(clientId, currTime);

        //refilling bucket if allowed
        Long windowEntryTime = clientLastRefillTime.get(clientId);
        int currToken = clientAndAllowedRequest.get(clientId);
        long timeElapsed = (currTime - windowEntryTime)/1000;
        if(timeElapsed > 0){
            currToken = (int) min(bucketSize, currToken + refillRate * timeElapsed);
            clientAndAllowedRequest.put(clientId, currToken);
            clientLastRefillTime.put(clientId, currTime);
        }

        //handling request
        if(currToken != 0){
            clientAndAllowedRequest.put(clientId, currToken - 1);
            return true;
        }
        return false;
    }
}
