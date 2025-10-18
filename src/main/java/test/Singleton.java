package test;

/**
 * @author dongli
 * @create 2025/10/18 14:21
 * @desc
 */

public class Singleton {
    static int count1;
    static int count2 = 0;
    private static Singleton singleton = new Singleton();


    private Singleton() {
        count1++;
        count2++;
    }

    public static Singleton getInstance() {
        return singleton;
    }

    public static void main(String[] asd) {
        Singleton singleton = Singleton.getInstance();
        System.out.println(singleton.count1);
        System.out.println(singleton.count2);
    }
}
