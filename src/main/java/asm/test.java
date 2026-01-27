import java.util.ArrayList;
import java.util.List;

public class test {
    // 面试题1: 静态变量的陷阱
    private static List<String> staticList = new ArrayList<>();

    public static List<String> badFunc1() {
        staticList.add("default");
        return staticList;
    }

    // 面试题2: 可变集合作为类成员变量的陷阱
    private List<String> memberList = new ArrayList<>();

    public List<String> badFunc2() {
        memberList.add("default");
        return memberList;
    }

    // 面试题3: 字符串拼接的性能问题
    public static String badStringConcat(int n) {
        String result = "";
        for (int i = 0; i < n; i++) {
            result += "a";  // 每次都会创建新对象
        }
        return result;
    }

    // 面试题4: 对象引用的陷阱
    public static void badReference() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = list1;  // 引用传递
        list2.add("modified");
        System.out.println("list1: " + list1);  // 也会被修改
    }

    public static void integerCacheTrap() {
        Integer a = 127;
        Integer b = 127;
        System.out.println(a == b);

        Integer c = 128;
        Integer d = 128;
        System.out.println(c == d);
    }

    public static void main(String[] args) {
        // 测试1: 静态变量陷阱
        System.out.println("=== 测试1: ");
        System.out.println(badFunc1());
        System.out.println(badFunc1());
        System.out.println(badFunc1());

        // 测试2: 成员变量陷阱
        System.out.println("\n=== 测试2:");
        test obj1 = new test();
        System.out.println(obj1.badFunc2());
        System.out.println(obj1.badFunc2());

        test obj2 = new test();
        System.out.println(obj2.badFunc2());

        // 测试3: 对象引用陷阱
        System.out.println("\n=== 测试3:");
        badReference();

        // 测试4: Integer 缓存陷阱
        System.out.println("\n=== 测试4:");
        integerCacheTrap();
        Integer d = 128;
        System.out.println("d+3:" + (d+3));
    }
}

