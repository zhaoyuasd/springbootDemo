package test;

import cn.hutool.core.bean.BeanUtil;

/**
 * @author dongli
 * @create 2024/7/24 16:32
 * @desc
 */

public class TestCast {
    public static void testCast() {
        try {
            Object util = new BeanUtil();
            Long ss = (Long) util;
        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        testCast();
    }


}


