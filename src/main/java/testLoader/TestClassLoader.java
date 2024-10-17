package testLoader;


import java.io.*;
import java.lang.reflect.Method;


/**
 * @author dongli
 * @create 2023/7/21 15:29
 * @desc
 */

public class TestClassLoader {
    public static void main(String[] args) throws Exception {

        ClassLoader oneLoader = new MyClassLoader(TestClassLoader.class.getClassLoader());

        ClassLoader loaderPerson = new MyClassLoaderSecond(oneLoader);
        ClassLoader loaderComputer = new MyClassLoaderSecond(oneLoader);

        // Person person = (Person) loaderPerson.loadClass("testLoader.Person").newInstance();
        // Computer computer = (Computer) loaderComputer.loadClass("testLoader.Computer").newInstance();
        // person.useComputer(computer);
        Object person =  loaderPerson.loadClass("testLoader.Person").newInstance();
        Object computer = loaderComputer.loadClass("testLoader.Computer").newInstance();
        System.out.println(person.getClass().getName());
        System.out.println(computer.getClass().getName());

        Method[] methods = person.getClass().getMethods();
        Method targetMethod = null;
        for (Method method : methods) {
            if (method.getName().equals("useComputer")) {
                targetMethod = method;
                break;
            }
        }
        System.out.println(targetMethod.getName());
        targetMethod.invoke(person, computer);
    }
}

class MyClassLoader extends ClassLoader{
    MyClassLoader(ClassLoader loader) {
        super(loader);
    }
    @Override
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        if(name.indexOf("Person") < 0 && name.indexOf("Computer") < 0) {
            return super.loadClass(name, resolve);
        }
        String cName = name.substring(name.indexOf(".")+1);

        String path = "D:\\mycode\\springbootDemo\\target\\classes\\testLoader\\";
        File file = new File(path + cName + ".class");
        InputStream in = null;
        byte[] b;
        try {
            in = new FileInputStream(file);
            b = new byte[in.available()];
            in.read(b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return defineClass(name, b, 0, b.length);
    }
}

class MyClassLoaderSecond extends ClassLoader{
    MyClassLoaderSecond (ClassLoader loader) {
        super(loader);
    }
    @Override
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }
}