package RatelimiterImplementation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class LeakyBucketRateLimiter implements RateLimiter{

    int bucketSize;
    int outflowRate;
    Map<String, Queue<Long>> clientAndNumberOfRequest;
    Map<String, Long> clientAndLastOutflowTime;

    public LeakyBucketRateLimiter(int bucketSize, int outflowRate){
        this.bucketSize = bucketSize;
        this.outflowRate = outflowRate;
        clientAndNumberOfRequest = new HashMap<>();
        clientAndLastOutflowTime = new HashMap<>();
    }

    @Override
    public boolean allowRequest(String clientId) {
        long currTime = System.currentTimeMillis();

        //onboard user if not present
        clientAndNumberOfRequest.putIfAbsent(clientId, new LinkedList<>());
        clientAndLastOutflowTime.putIfAbsent(clientId, currTime);

        Queue<Long> requestHistory = clientAndNumberOfRequest.get(clientId);

        //outflow request
        long elapsedSeconds = (currTime - clientAndLastOutflowTime.get(clientId)) / 1000;
        long requestToLeak =  outflowRate * elapsedSeconds;

        while(!requestHistory.isEmpty() && requestToLeak > 0){
            requestHistory.poll();
            requestToLeak--;
        }

        if(elapsedSeconds > 0){
            clientAndLastOutflowTime.put(clientId, currTime);
        }

        //handling request
        if(requestHistory.size() < bucketSize){
            requestHistory.add(currTime);
            return true;
        }
        return false;
    }
}
