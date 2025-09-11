package other;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dongli
 * @create 2025/7/1 17:12
 * @desc
 */

@Getter
@Setter
public class InvTmHj {
    @ExcelProperty("商品编号")
    private String skuNo;

    @ExcelProperty("商品名称")
    private String  skuName;

    @ExcelProperty("美素天猫皇家桐庐新仓")
    private Integer  WH00000436;

    @ExcelProperty("美素皇家天猫东莞仓")
    private Integer WH00000338;

    @ExcelProperty("美素皇家天猫天津仓")
    private Integer WH00000326;

    @ExcelProperty("美素皇家天猫成都仓")
    private String WH00000334;

    @ExcelProperty("皇家美素信阳仓")
    private String WH00000413;
}
