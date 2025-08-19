import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dongli
 * @create 2025/7/1 17:02
 * @desc
 */

@Getter
@Setter
public class InvTmYu {

    @ExcelProperty("商品编号")
    private String skuNo;

    @ExcelProperty("商品名称")
    private String  skuName;

    @ExcelProperty("美素天猫源悦桐庐新仓")
    private Integer  WH00000424;

    @ExcelProperty("美素金装天猫东莞仓")
    private Integer WH00000337;

    @ExcelProperty("美素金装天猫天津仓")
    private Integer WH00000325;

    @ExcelProperty("美素金装天猫成都仓")
    private String WH00000333;

    @ExcelProperty("源悦美素信阳仓")
    private String WH00000414;
}
