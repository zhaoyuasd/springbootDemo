package upgradeEle;


import java.util.concurrent.CountDownLatch;

/**
 * @author: dongli
 * @since: 2026/4/28 9:49
 * @description:
 */

public class LockCutDown {
   private static final CountDownLatch latch = new CountDownLatch(3);

   public static void toWait() {
       try {
           latch.await();
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
   }

   public static void finish() {
       latch.countDown();
   }
}
