package asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.ASM4;

/**
 * @author dongli
 * @create 2024/4/25 15:35
 * @desc
 */

public class RemoveNopAdapter extends MethodVisitor {
    public RemoveNopAdapter(MethodVisitor mv) {
        super(ASM4, mv);
    }
    @Override
    public void visitInsn(int opcode) {
        if (opcode != Opcodes.NOP) {
            mv.visitInsn(opcode);
        }
    }
}
