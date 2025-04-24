import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author dongli
 * @create 2025/4/9 10:20
 * @desc
 */

public class TestList {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1"); list.add("1");list.add("1.2");list.add("new Object()");

        List<String> list2 = new ArrayList<>();
        list2.add("1.2");list2.add("new Object()");list2.add("1"); list2.add("1");

        System.out.println(Objects.equals(list, list2));
        System.out.println(list.containsAll(list2));
    }
}
