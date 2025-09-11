package other;

import cn.hutool.core.map.MapUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2025/7/1 17:00
 * @desc
 */

public class DealExcel {
    /**
     * update sales_order_sku_occupy so, erp_prod_friso.sku sku set so.skuId = sku.id where
     * so.skuNo = sku.skuNo and so.createTime > '2025-07-01 18:00:00'
     * @param args
     */
    public static void main(String[] args) {
        String filePath1 = "D:/huangjia.xlsx";
        // 方式2: 同步读取(适合小数据量)
        Map<String, InvInfo> map = new HashMap<>();
        List<Map> product1 = EasyExcel.read(filePath1)
                .head(InvTmHj.class)
                .sheet()
                .doReadSync().stream().map(e -> {
                    try {
                        System.out.println(JSON.toJSONString(e));
                        return toMap(e);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                }).collect(Collectors.toList());

        System.out.println("-------------------------------------------");
        String filePath2 = "D:/yuanyue.xlsx";
        // 方式2: 同步读取(适合小数据量)
        List<Map> products2 = EasyExcel.read(filePath2)
                .head(InvTmYu.class)
                .sheet()
                .doReadSync().stream().map(e -> {
                    try {
                        System.out.println(JSON.toJSONString(e));
                        return toMap(e);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                }).collect(Collectors.toList());

        System.out.println("-------------------------------------------");
        String filePath3 = "D:/ertong.xlsx";
        // 方式2: 同步读取(适合小数据量)
        List<Map> product3 = EasyExcel.read(filePath3)
                .head(InvErT.class)
                .sheet()
                .doReadSync().stream().map(e -> {
                    try {
                        System.out.println(JSON.toJSONString(e));
                        return toMap(e);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                }).collect(Collectors.toList());
        System.out.println("-------------------------------------------");
        List<Map> all = new ArrayList<>();
        all.addAll(product1);
        all.addAll(products2);
        all.addAll(product3);


        Long id = 4324241787728383835L;

        for (Map<String, Object> tmpMap : all) {
            if (MapUtil.isEmpty(tmpMap)) {
                continue;
            }
            try {
                String skuNo = tmpMap.get("skuNo").toString();
                String skuName = tmpMap.get("skuName").toString();
                tmpMap.remove("skuNo");
                tmpMap.remove("skuName");
                tmpMap.forEach((k, v) -> {
                    String stockCode = k;
                    Integer freezeQty = covertToIn(v);
                    InvInfo tmpInfo = map.computeIfAbsent(stockCode + "," + skuNo, ak -> {
                        InvInfo invInfo = new InvInfo();
                        invInfo.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
                        invInfo.setSkuNo(skuNo);
                        invInfo.setStockCode(stockCode);
                        invInfo.setFreezeQty(0);
                        String stockId = WarehouseCode.fromWarehouseNumber(stockCode).getCode();
                        invInfo.setStockId(Long.valueOf(stockId));
                        return invInfo;
                    });
                    tmpInfo.setFreezeQty(freezeQty + tmpInfo.getFreezeQty());
                });
            } catch (Exception ex) {
                System.out.println(JSON.toJSONString(tmpMap));
                throw new RuntimeException(ex);
            }
        }

        map.values().forEach(e -> System.out.println(e.toString()));
    }

    private static Integer covertToIn(Object v) {
     if (v instanceof Integer) {
         return (Integer) v;
     }
     return Integer.valueOf(v.toString());
    }


    public static Map<String, Object> toMap(Object e) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = e.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // 允许访问私有字段
            map.put(field.getName(), field.get(e));
        }
        return map;
    }
}
