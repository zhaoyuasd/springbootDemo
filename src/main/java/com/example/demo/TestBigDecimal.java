package com.example.demo;

import java.math.BigDecimal;

/**
 * @author dongli
 * @create 2023/5/17 9:32
 * @desc
 */

public class TestBigDecimal {
    public static void main(String[] args) {
        BigDecimal a = BigDecimal.valueOf(1.6);
        BigDecimal b = BigDecimal.valueOf(3);
        BigDecimal c = BigDecimal.valueOf(1.6);
        /**
         *  a > b  a.compareTo(b) > 0
         */
        //System.out.println(a.compareTo(b));

       // System.out.println(a.compareTo(c));

        System.out.println(a.toString());
        System.out.println(b.toString());
    }
}
