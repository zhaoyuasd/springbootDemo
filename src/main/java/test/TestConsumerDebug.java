package test;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author dongli
 * @create 2023/11/4 16:19
 * @desc
 */

public class TestConsumerDebug {
    public static void main(String[] args) {

        Executor executor = new ThreadPoolExecutor(2 ,2, 1000, TimeUnit.SECONDS,  new ArrayBlockingQueue(10));

        executor.execute(() -> outPrnit("asd2"));
        executor.execute(() -> outPrnit("asd1"));
    }

    private static void outPrnit(String str) {
        String thName = Thread.currentThread().getName();
         str = str + " 111";
         str = str + " 123";
         str = str + " 234";
        System.out.println(thName + " " + str);
    }
}
