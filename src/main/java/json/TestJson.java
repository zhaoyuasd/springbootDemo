package json;

import com.alibaba.fastjson2.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dongli
 * @create 2026/3/2 14:06
 * @desc
 */

public class TestJson {
    public static void main(String[] args) {
        String customerJSONValue ="{1:'红色',2:'黄色',3:'绿色',4:'蓝色',5:'紫色'}";
        Map json = JSON.parseObject(customerJSONValue, HashMap.class);
        Object firstVal = 5;
        Object secondVal = 3;
        System.out.println(json.getOrDefault(firstVal, firstVal) + "-----" + json.get(firstVal));
        System.out.println(json.getOrDefault(secondVal, secondVal) + "-----" + json.get(secondVal));

    }
}
