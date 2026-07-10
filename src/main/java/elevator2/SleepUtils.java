package elevator2;


/**
 * @author: dongli
 * @since: 2026/7/10 10:49
 * @description:
 */

public class SleepUtils {
    public static void sleep(Long mis) {
        try {
            Thread.sleep(mis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
