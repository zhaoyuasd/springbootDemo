package util;



import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dongli
 * @create 2023/6/14 14:05
 * @desc  抖音超市 账单实体类
 */


public class TikTokCsBill {

    private Long id;

    @ImportExcelColumnProperty(desc = "序号")
    private Long order;

    @ImportExcelColumnProperty(desc = "金额类型")
    private String fullPayType;

    @ImportExcelColumnProperty(desc = "明细单号", conversion = ImportExcelColumnProperty.OtherToStringConversion.class)
    private String detailCode;

    @ImportExcelColumnProperty(desc = "采购单号")
    private String purchaseCode;

    @ImportExcelColumnProperty(desc = "销售单号", conversion = ImportExcelColumnProperty.OtherToStringConversion.class)
    private String salesCode;

    @ImportExcelColumnProperty(desc = "货品ID", conversion = ImportExcelColumnProperty.OtherToStringConversion.class)
    private String goodId;

    @ImportExcelColumnProperty(desc = "货品名称")
    private String goodName;

    @ImportExcelColumnProperty(desc = "货品规格")
    private String goodSpec;

    @ImportExcelColumnProperty(desc = "供货单价（单位元）", conversion = ImportExcelColumnProperty.NumberToBigDecimalConversion.class)
    private BigDecimal price;

    @ImportExcelColumnProperty(desc = "数量", conversion = ImportExcelColumnProperty.OtherToIntegerConversion.class)
    private Integer qty;

    @ImportExcelColumnProperty(desc = "结算金额（单位元）", conversion = ImportExcelColumnProperty.NumberToBigDecimalConversion.class)
    private BigDecimal totalAmount;

    @ImportExcelColumnProperty(desc = "仓位")
    private String location;

    @ImportExcelColumnProperty(desc = "创建时间", conversion = ImportExcelColumnProperty.StringToDateConversion.class)
    private Date billCreateTime;

    @ImportExcelColumnProperty(desc = "税率", conversion = ImportExcelColumnProperty.NumberToBigDecimalConversion.class)
    private BigDecimal tax;

    @ImportExcelColumnProperty(desc = "备注")
    private String remark;

    @ImportExcelColumnProperty(desc = "到账时间", conversion = ImportExcelColumnProperty.StringToDateConversion.class)
    private Date accountingDate;

    //所属渠道
    private Long chnId;

    //品牌Id
    private String brandId;
    /**
     * 导入记录ID
     */
    private Long billRecordId;
}
