package asm;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author dongli
 * @create 2024/4/19 14:14
 * @desc
 */

public class TestMain {
    public static void main(String[] args) throws Exception {
       // testRead();
       // testWrite();
       /* */
        //ClassReader reader = new ClassReader( testWrite());
       // ClassPrinter printer = new ClassPrinter();
       // reader.accept(printer, 0);
       // ASMifier.main(new String[]{"asm.Bean"});
        ClassReader reader = new ClassReader("asm.TestDemoClass");

        ClassNode classNode = new ClassNode();
        reader.accept(classNode,0);

        ClassWriter cw = new ClassWriter(0);
        classNode.accept(cw);

       // TraceClassVisitor tcv = new TraceClassVisitor(cw, new PrintWriter(System.out));
        //ClassWriter cw = new ClassWriter(0);
        CheckClassAdapter ttcv = new CheckClassAdapter(cw);
        RemoveNopClassAdapter cv = new RemoveNopClassAdapter(ttcv);
        AddTimerAdapter addTimerAdapter = new AddTimerAdapter(cv);
        reader.accept(addTimerAdapter, 0);

        MyClassLoader loader = new MyClassLoader(ClassLoader.getSystemClassLoader());
        Class<?> clz = loader.defineMyClass("asm.TestDemoClass", cw.toByteArray());
        Object obj = clz.newInstance();
        Method method = obj.getClass().getMethod("show");
        method.invoke(obj);
        Field field = obj.getClass().getField("timer");
        field.setAccessible(true);
        Long value = (Long) field.get(obj);
        System.out.println(value);

    }


    /**
     * package pkg;
     * public interface Comparable extends Mesurable {
     * int LESS = -1;
     * int EQUAL = 0;
     * int GREATER = 1;
     * int compareTo(Object o);
     * }
     */
    private static  byte[]  testWrite() {

        ClassWriter cw = new ClassWriter(0);
        TraceClassVisitor tcv = new TraceClassVisitor(cw, new PrintWriter(System.out));
        CheckClassAdapter ttcv = new CheckClassAdapter(tcv);
        RemoveNopClassAdapter cv = new RemoveNopClassAdapter(ttcv);
        cv.visit(V1_7, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "asm/Comparable", null, "java/lang/Object",
                new String[]{"asm/Mesurable"});
        cv.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, new Integer(-1)).visitEnd();
        cv.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, new Integer(0)).visitEnd();
        cv.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, new Integer(1)).visitEnd();
        cv.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cv.visitEnd();
        byte[] b = cw.toByteArray();
        return b;
    }

    private static void testRead() throws IOException {
        ClassReader reader = new ClassReader("java.lang.Runnable");
        ClassPrinter printer = new ClassPrinter();
        reader.accept(printer, 0);
    }
}
