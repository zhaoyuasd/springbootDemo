package asm;

import org.objectweb.asm.tree.MethodNode;

/**
 * @author dongli
 * @create 2024/7/18 18:50
 * @desc
 */

public class MethodTransformer {
    protected MethodTransformer ct;
    public MethodTransformer(MethodTransformer ct) {
        this.ct = ct;
    }
    public void transform(MethodNode cn) {
        if (ct != null) {
            ct.transform(cn);
        }
    }
}
