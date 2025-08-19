package random;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2025/8/19 14:06
 * @desc  随机分布 自平衡实现
 */

public class TestAutoAvgRandom {
    public static void main(String[] args) {

        List<DbDemo> list = getDbDemos();
        int total = list.stream().mapToInt(e -> e.currentCount).sum();
        int length = 1000;
        // 按照已存在的数据 升序排列 从少到对
        long startTime = System.currentTimeMillis();
        for (int j = 0; j < 10000 * 10; j++) {
            list.sort(Comparator.comparingInt(a -> a.currentCount));
            BigDecimal totalBigDecimal = new BigDecimal(total);
            List<BigDecimal> percent = list.stream()
                    .map(a -> BigDecimal.valueOf(a.currentCount).divide(totalBigDecimal, 3, RoundingMode.HALF_UP))
                    .sorted(Comparator.comparingDouble(BigDecimal::doubleValue)).collect(Collectors.toList());

            // 升序排列

            // 百分比修正

            BigDecimal left = BigDecimal.ONE;
            for (BigDecimal b : percent) {
                left = left.subtract(b);
            }
            percent.set(percent.size() - 1, percent.get(percent.size() - 1).add(left));

            // 倒序 从大到小
            Collections.reverse(percent);

            List<TmpDemo> tmpDemos = new ArrayList<>();
            // 计算选择哪个库 根据百分比 倒排数据
            int start = 0;
            BigDecimal lengthBigDecimal = BigDecimal.valueOf(length);
            for (int i = 0; i < list.size(); i++) {
                TmpDemo tmpDemo = new TmpDemo();
                tmpDemo.setDbDemo(list.get(i));
                tmpDemo.setFromIndex(start);
                //  分配 选库的区间长度 数据少的 选库的区间长度就长  可以自适应
                start = start + lengthBigDecimal.multiply(percent.get(i)).intValue();
                tmpDemo.setEndIndex(start);
                tmpDemos.add(tmpDemo);
            }
            Random random = new Random();
            int dbSelect = random.nextInt(length); // [0, 1000]

            for (TmpDemo tmpDemo : tmpDemos) {
                if (dbSelect >= tmpDemo.getFromIndex() && dbSelect < tmpDemo.getEndIndex()) {
                    int tableSelect = random.nextInt(128) + tmpDemo.dbDemo.fromIndex;
                    tmpDemo.dbDemo.list.add(tableSelect);
                    total +=1;
                    tmpDemo.dbDemo.currentCount++;
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("take :"  + (endTime - startTime) / 1000);
        list.forEach(System.out::println);
    }

    private static List<DbDemo> getDbDemos() {
        DbDemo dbDemo128 = new DbDemo(5000, 0, 127, new ArrayList<>());
        DbDemo dbDemo256 = new DbDemo(4000, 128, 255, new ArrayList<>());
        DbDemo dbDemo384 = new DbDemo(1000, 256, 383, new ArrayList<>());
        DbDemo dbDemo512 = new DbDemo(0, 384, 511, new ArrayList<>());
        DbDemo dbDemo640 = new DbDemo(0, 512, 640, new ArrayList<>());

        List<DbDemo> list = new ArrayList<>();
        list.add(dbDemo128);
        list.add(dbDemo256);
        list.add(dbDemo384);
        list.add(dbDemo512);
        list.add(dbDemo640);
        return list;
    }
}
