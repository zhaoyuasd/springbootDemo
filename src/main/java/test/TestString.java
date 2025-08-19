package test;

/**
 * @author dongli
 * @create 2024/10/21 17:18
 * @desc
 */

public class TestString {
    public static void main(String[] args) {
      /*String[] tmp = new String[args.length];
      for (int i = 0; i < args.length; i++) {
          tmp[i] = args[i];
      }*/
       /* String tt ="asdasd,,qedasfq3,";
        for (String item : tt.split(",")) {
            System.out.println(item);
        }*/

        String skuSet = "qweqe**23";
        int index =  skuSet.lastIndexOf("*");
        String skuNo = skuSet.substring(0, index);
        String num = skuSet.substring(index + 1);

        System.out.println(skuNo);
        System.out.println(num);
    }
}
