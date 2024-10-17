package asm;

import org.checkerframework.checker.units.qual.C;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author dongli
 * @create 2024/7/18 18:59
 * @desc
 */

public class TestMethod {
    public static void main(String[] args) throws IOException {
        ClassReader reader = new ClassReader("asm.Bean");

        System.out.println("==========================before===========================");
        TraceClassVisitor visitor1 = new TraceClassVisitor(new PrintWriter(System.out));
        reader.accept(visitor1, 0);


        ClassNode classNode = new ClassNode();

        reader.accept(classNode, 0);

        OptimizeJumpTransformer transformer = new OptimizeJumpTransformer(null);
        classNode.methods.forEach(transformer::transform);

        System.out.println("==========================after===========================");
        ClassWriter writer =new ClassWriter(0);
        classNode.accept(writer);

        TraceClassVisitor visitor2 = new TraceClassVisitor(new PrintWriter(System.out));
        ClassReader reader2 = new ClassReader(writer.toByteArray());
        reader2.accept(visitor2, 0);
    }
}
