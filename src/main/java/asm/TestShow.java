package asm;

import istio.v1.auth.Ca;
import org.objectweb.asm.util.ASMifier;

import java.io.IOException;

/**
 * @author dongli
 * @create 2024/4/25 16:21
 * @desc
 */

public class TestShow {
    public static void main(String[] args) throws Exception {
        //ASMifier.main(new String[]{"asm.Bean"});
        Bean bean = new Bean();
        bean.showOrder("nama",2, bean);

        try {
            int i = 1 /10;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
