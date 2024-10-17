import static java.lang.invoke.MethodHandles.lookup;

import java.lang.invoke.*;

public class InvokeDynamicTest{
    public static void main(String[]args)throws Throwable{
        INDY_BootstrapMethod().invokeExact("icyfenix");


        MethodType methodType = MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V",null);
        MethodHandle handle =  lookup().findStatic(InvokeDynamicTest.class,"testMethod",methodType);
        CallSite callSite = LambdaMetafactory.metafactory(
                lookup(),
                "testMethod",
                methodType,
                methodType,
                handle,
                methodType);

      //  callSite.dynamicInvoker().invokeExact("icyfenix");
    }
    public static void testMethod(String s){
        System.out.println("hello String："+s);
    }
    public static CallSite BootstrapMethod(MethodHandles.Lookup lookup,String name,MethodType mt)throws Throwable{
        return new ConstantCallSite(lookup.findStatic(InvokeDynamicTest.class,name,mt));
    }

    private static MethodHandle INDY_BootstrapMethod()throws Throwable{
        MethodType methodType = MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",null);
        MethodHandle handle =  lookup().findStatic(InvokeDynamicTest.class,"BootstrapMethod",methodType);
        CallSite cs=(CallSite)handle.invokeWithArguments(lookup(),"testMethod",MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V",null));
        return cs.dynamicInvoker();
    }
}