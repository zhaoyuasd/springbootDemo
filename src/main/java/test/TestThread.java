package test;

import java.util.Map;

/**
 * @author dongli
 * @create 2024/10/22 10:15
 * @desc
 */

public class TestThread {
    public static void main(String[] args) {
        Map<Thread, StackTraceElement[]> threadMap = Thread.getAllStackTraces();
        threadMap.forEach((k, v) -> {
            System.out.println(k.getId() + " -- " + k.getName());
            for (StackTraceElement st : v) {
                 System.out.println(st.getClassName());
            }
        });
    }
}
