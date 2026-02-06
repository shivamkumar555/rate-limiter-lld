package RatelimiterImplementation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SlidingWindowRateLimiter implements RateLimiter{
    int noOfRequestPerUnitTime;
    Long timeWindow;
    Map<String, Queue<Long>> clientRequestHistory;

    public SlidingWindowRateLimiter(int noOfRequestPerUnitTime, Long timeWindow) {
        this.noOfRequestPerUnitTime = noOfRequestPerUnitTime;
        this.timeWindow = timeWindow;
        this.clientRequestHistory = new HashMap<>();
    }

    @Override
    public boolean allowRequest(String clientId) {
        Long currTime = System.currentTimeMillis();

        //checking if user is new
        clientRequestHistory.putIfAbsent(clientId, new LinkedList<>());

        //need to remove outdated time from sliding window
        Queue<Long> requestHistory = clientRequestHistory.get(clientId);

        while(!requestHistory.isEmpty() && currTime - requestHistory.peek() > timeWindow){
            requestHistory.poll();
        }

        if(requestHistory.size() < noOfRequestPerUnitTime){
            requestHistory.add(currTime);
            return true;
        }
        return false;
    }
}
