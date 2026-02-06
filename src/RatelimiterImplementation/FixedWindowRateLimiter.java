package RatelimiterImplementation;

import java.util.HashMap;
import java.util.Map;

public class FixedWindowRateLimiter implements RateLimiter{

    int maxRequestCount;
    long timeToReset;
    Map<String, Integer> clientAndCount;
    Map<String, Long> clientAndTime;

    public FixedWindowRateLimiter(int maxRequestCount, long timeToReset){
        this.maxRequestCount = maxRequestCount;
        this.timeToReset = timeToReset;
        this.clientAndCount = new HashMap<>();
        this.clientAndTime = new HashMap<>();
    }

    @Override
    public boolean allowRequest(String clientId) {

        long currTime = System.currentTimeMillis();

        //onboard user
        clientAndCount.putIfAbsent(clientId, 0);
        clientAndTime.putIfAbsent(clientId,  currTime);

        //resetting cnt if time frame is greater than allowed time
        long lastentrytime = clientAndTime.get(clientId);
        if(currTime - lastentrytime >= timeToReset) {
            clientAndCount.put(clientId, 0);
            clientAndTime.put(clientId, currTime);
        }

        //checking if threshold reaches or not
        int noOfCurrRequest = clientAndCount.get(clientId);
        if(noOfCurrRequest < maxRequestCount){
            clientAndCount.put(clientId, noOfCurrRequest + 1);
            return true;
        }
        return false;
    }
}
