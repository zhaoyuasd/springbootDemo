package asm;

import org.objectweb.asm.tree.ClassNode;

/**
 * @author dongli
 * @create 2024/7/11 22:02
 * @desc
 */

public class ClassTransformer {
    protected ClassTransformer ct;
    public ClassTransformer(ClassTransformer ct) {
        this.ct = ct;
    }
    public void transform(ClassNode cn) {
        if (ct != null) {
            ct.transform(cn);
        }
    }
}
