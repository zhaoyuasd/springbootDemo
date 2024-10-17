package util;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author dongli
 * @create 2023/8/16 10:14
 * @desc WMS发货回传异常(三方仓库发货 回调异常）
 */

@Getter
@Setter
public class WmsFeedExceptionOrder {

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

    /**
     * 所属渠道Id
     */
    @ImportExcelColumnProperty(desc = "所属渠道Id")
    private Long chnId;

    /**
     * 所属渠道
     */
    @ImportExcelColumnProperty(desc = "所属渠道")
    private String chnName;


    /**
     * 发货仓库Id
     */
    @ImportExcelColumnProperty(desc = "发货仓库Id")
    private Long stockId;

    /**
     * 发货仓库
     */
    @ImportExcelColumnProperty(desc = "发货仓库")
    private String stockName;

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
    @ImportExcelColumnProperty(desc = "内部备注")
    private String interiorMemo;

    /**
     * 省份
     */
    @ImportExcelColumnProperty(desc = "省份")
    private String province;

    /**
     * 城市
     */
    @ImportExcelColumnProperty(desc = "城市")
    private String city;

    /**
     * 区县
     */
    @ImportExcelColumnProperty(desc = "区县")
    private String county;

    /**
     * 出库单的商品信息
     */
    @ImportExcelColumnProperty(desc = "出库单的商品信息")
    private String orderSkuInfo;


    /**
     * 品牌id
     */
    @ImportExcelColumnProperty(desc = "品牌id")
    private String brandId;

    /**
     * 创建时间
     */
    @ImportExcelColumnProperty(desc = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ImportExcelColumnProperty(desc = "更新时间")
    private Date updateTime;

    /**
     * 操作人
     */
    @ImportExcelColumnProperty(desc = "操作人")
    private String operator;
}
