package test;

import java.util.StringTokenizer;

/**
 * @author dongli
 * @create 2024/10/21 17:18
 * @desc
 */

public class TestString {
    public static void main(String[] args) {
        String ss ="asdsad,asdas，ASDADS";
        StringTokenizer tokenizer = new StringTokenizer(ss, ",，");
       while (tokenizer.hasMoreTokens()) {
           System.out.println(tokenizer.nextToken());
       }
    }
}
