package test;

/**
 * @author dongli
 * @create 2025/10/18 14:26
 * @desc
 */

public class View {
    static View vv=new View();  // 静态代码块
    static int ss=0;
    //内部初始化 不执行静态代码 这里会合并到每个构造方法里面 一般不这么写
    {
        System.out.println("1");
    }

    static {
        System.out.println("2");
    }

    View(){
        System.out.println("3");
        ss=23;
        System.out.println(ss);
    }
}
