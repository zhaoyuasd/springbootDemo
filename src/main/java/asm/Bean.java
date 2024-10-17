package asm;

/**
 * @author dongli
 * @create 2024/4/23 17:14
 * @desc
 */

public class Bean {
    private int f;
    public int getF() {
        return this.f;
    }

    public void setF(int f) {
        if (f >= 0) {
            this.f = f;
        }
        int r = 20;
        int g =30;
        while (r < 30) {
            r ++;
        }
        int t = 30;
    }

    public void showOrder(String name, Integer as, Bean bean) throws Exception {

    }

}
