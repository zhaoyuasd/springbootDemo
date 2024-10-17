package util;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author dongli
 * @create 2023/8/14 18:53
 * @desc 平台发货失败 实体
 */

@Setter
@Getter
public class PlatFormFeedExceptionOrder {


    private Long id;

    /**
     * 出库单号
     */
    @ImportExcelColumnProperty(desc = "出库单号")
    private String doCode;

    /**
     * 渠道交易号
     */
    @ImportExcelColumnProperty(desc = "渠道交易号")
    private String topTids;

    /**
     * 订单号
     */
    @ImportExcelColumnProperty(desc = "订单号")
    private String erpCode;

    @ImportExcelColumnProperty(desc = "渠道id")
    private String chnId;

    /**
     * 所属渠道
     */
    @ImportExcelColumnProperty(desc = "所属渠道")
    private String chnName;

    /**
     * 付款时间
     */
    @ImportExcelColumnProperty(desc = "付款时间")
    private Date payTime;

    /**
     * 下单时间
     */
    @ImportExcelColumnProperty(desc = "下单时间")
    private Date buyTime;

    /**
     * 最晚发货时间
     */
    @ImportExcelColumnProperty(desc = "最晚发货时间")
    private Date lastSendDate;

    @ImportExcelColumnProperty(desc = "发货仓库Id")
    private Long stockId;
    /**
     * 发货仓库
     */
    @ImportExcelColumnProperty(desc = "发货仓库")
    private String stockName;

    /**
     * 回传时间
     */
    @ImportExcelColumnProperty(desc = "回传时间")
    private Date feedbackTime;

    /**
     * 回传异常原因
     */
    @ImportExcelColumnProperty(desc = "回传异常原因")
    private String errorMsg;

    /**
     * 买家备注
     */
    @ImportExcelColumnProperty(desc = "买家备注")
    private String buyerMemo;

    /**
     * 卖家备注
     */
    @ImportExcelColumnProperty(desc = "卖家备注")
    private String sellerMemo;

    /**
     * 内部备注
     */
    @ImportExcelColumnProperty(desc = "interiorMemo")
    private String interiorMemo;

    @ImportExcelColumnProperty(desc = "物流公司Id")
    private Long expressId;
    /**
     * 物流公司
     */
    @ImportExcelColumnProperty(desc = "物流公司")
    private String expressName;

    /**
     * 物流编号
     */
    @ImportExcelColumnProperty(desc = "物流编号")
    private String expCode;

    /**
     * 出库单的商品信息
     */
    @ImportExcelColumnProperty(desc = "物流编号")
    private String orderSkuInfo;
}
