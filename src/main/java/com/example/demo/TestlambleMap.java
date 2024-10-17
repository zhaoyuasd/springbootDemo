package com.example.demo;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestlambleMap {
    public static void main(String[] args) {
        List<KeyValue>  list = new ArrayList<>();

        for (int i = 99; i < 103; i++) {
            KeyValue keyValue = new KeyValue();
            keyValue.key = BigDecimal.valueOf(12);
            keyValue.value = BigDecimal.valueOf(i);
            list.add(keyValue);
        }
        //  Duplicate key 99
        Map<BigDecimal, BigDecimal> map = list.stream().collect(Collectors.toMap(e -> e.key, e -> e.key));

    }


    public static class KeyValue {
        public BigDecimal key;
        public BigDecimal value;
    }
}
