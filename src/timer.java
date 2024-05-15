//import java.util.Date;
//import java.util.*;
//
//public class timer {
//
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("hello");
//        int x= scanner.nextInt();
//        if(x==3){
//            long startTime = System.currentTimeMillis();
//            long elapsedTime = 0L;
//            long last = -1;
//            while (elapsedTime < 10*1000) {
//                //perform db poll/check
//                elapsedTime = (new Date()).getTime() - startTime;
//                if(elapsedTime%1000==0){
//                    if(last != elapsedTime){
//                        last = elapsedTime;
//                        System.out.println(elapsedTime/1000);
//                    }
//                }
//            }
//        }
//
//    }
//
//

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class timer{
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println("Task executed");
        executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }
}