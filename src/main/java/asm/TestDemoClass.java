package asm;

/**
 * @author dongli
 * @create 2024/4/25 16:04
 * @desc
 */

public class TestDemoClass {
   // private static  Long time;
    public void show() throws InterruptedException {
        //time -= System.currentTimeMillis();
        Thread.sleep(1000);
        System.out.println(" show() over");
       // time += System.currentTimeMillis();
       // System.out.println(time);
    }
}
