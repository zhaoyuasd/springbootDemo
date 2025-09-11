package other;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dongli
 * @create 2025/7/1 17:26
 * @desc
 */

@Getter
@Setter
public class InvErT {

    @ExcelProperty("商品编号")
    private String skuNo;

    @ExcelProperty("商品名称")
    private String  skuName;

    @ExcelProperty("美素天猫儿童粉桐庐仓")
    private Integer  WH00000636;
}
