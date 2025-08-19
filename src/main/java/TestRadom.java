import java.util.Random;

/**
 * @author dongli
 * @create 2025/8/19 11:19
 * @desc
 */

public class TestRadom {
    public static void main(String[] args) {


        Random random = new Random();
        new Thread(() -> {
            int less128 = 0;
            int less256 = 0;
            int less384 = 0;
            int less512 = 0;
            int less640 = 0;
            int less768 = 0;
            for (int i = 0; i < 10000 * 10; i++) {
                int j = random.nextInt(1280);
                if (j < 128) less128++;
                else if (j <256) less256++;
                else if (j < 384) less384++;
                else if (j < 512) less512++;
                else if (j < 640) less640++;
                else less768++;
            }
            System.out.println("1:" + less128);
            System.out.println("1:" + less256);
            System.out.println("1:" + less384);
            System.out.println("1:" + less512);
            System.out.println("1:" + less640);
            System.out.println("1:" + less768);
        }).start();
        new Thread(() -> {
            int less128 = 0;
            int less256 = 0;
            int less384 = 0;
            int less512 = 0;
            int less640 = 0;
            int less768 = 0;
            for (int i = 0; i < 10000 * 10; i++) {
                int j = random.nextInt(1280);
                if (j < 128) less128++;
                else if (j <256) less256++;
                else if (j < 384) less384++;
                else if (j < 512) less512++;
                else if (j < 640) less640++;
                else less768++;
            }
            System.out.println("1:" + less128);
            System.out.println("1:" + less256);
            System.out.println("1:" + less384);
            System.out.println("1:" + less512);
            System.out.println("1:" + less640);
            System.out.println("1:" + less768);
        }).start();
    }
}
