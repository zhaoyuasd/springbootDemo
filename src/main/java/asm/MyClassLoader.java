package asm;

/**
 * @author dongli
 * @create 2024/4/25 16:09
 * @desc
 */

public class  MyClassLoader  extends ClassLoader{
    MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    public final Class<?> defineMyClass(String name, byte[] b)
            throws ClassFormatError
    {
        return defineClass(name, b, 0, b.length, null);
    }

}
