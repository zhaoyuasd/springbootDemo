package caom.laozao.springbootdemo.salableinventory;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Salable inventory Excel row data.
 */
@Data
public class SalableInventoryExcel {

    @ExcelProperty(value = "日期", index = 0)
    private String date;

    @ExcelProperty(value = "产品编码", index = 1)
    private String skuNo;

    @ExcelProperty(value = "产品名称", index = 2)
    private String productName;

    @ExcelProperty(value = "类型", index = 3)
    private String type;

    @ExcelProperty(value = "数量", index = 4)
    private String qty;

    @ExcelProperty(value = "未发货数量", index = 5)
    private String noDoQty;

    @ExcelProperty(value = "品牌", index = 6)
    private String brands;

    @ExcelProperty(value = "实物箱规", index = 7)
    private String boxLimit;

    @ExcelProperty(value = "规格", index = 8)
    private String skuName;

    @ExcelProperty(value = "单位", index = 9)
    private String unit;

    @ExcelProperty(value = "门店编码", index = 10)
    private String storeCode;

    @ExcelProperty(value = "门店名称", index = 11)
    private String storeName;

    @ExcelProperty(value = "未发货库存", index = 12)
    private String unDelivery;

    @ExcelProperty(value = "OMS占用库存", index = 13)
    private String dcQude;
}
