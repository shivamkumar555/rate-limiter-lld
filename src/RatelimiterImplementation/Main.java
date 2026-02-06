package RatelimiterImplementation;

import RatelimiterImplementation.service.RateLimiterService;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        RateLimiterService service = new RateLimiterService();

//        RatelimiterImplementation.service.registerUser("user_1", "fixed", 3, 10);
        service.registerUser("user_2", "sliding", 3, 5);
//        RatelimiterImplementation.service.registerUser("user_3", "token-bucket", 5, 10);
//        RatelimiterImplementation.service.registerUser("user_4", "leaky-bucket", 3, 4);

        for (int i = 0; i < 7; i++) {
//            System.out.println("User 1 Request " + (i + 1) + " : " + RatelimiterImplementation.service.allowRequest("user_1"));
            System.out.println("User 2 Request " + (i + 1) + " : " + service.allowRequest("user_2"));
//            System.out.println("User 3 Request " + (i + 1) + " : " + RatelimiterImplementation.service.allowRequest("user_3"));
//            System.out.println("User 4 Request " + (i + 1) + " : " + RatelimiterImplementation.service.allowRequest("user_4"));
            Thread.sleep(1000);
        }
    }
}
