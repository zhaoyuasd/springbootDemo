package util;


/**
 * @author dongli
 * @create 2023/6/7 18:58
 * @desc
 *   抖音超市 销售退货单
 */

public class TiktokInboundOrder {

    private Long id;

    /**
     * 字段长度(32)：交易退货单号
     */
    @ImportExcelColumnProperty(desc = "字段长度(32)：交易退货单号")
    private String tradeReturnNo;
    /**
     * 字段长度(64)：入库单号
     */
    @ImportExcelColumnProperty(desc = "字段长度(64)：入库单号")
    private String inboundOrderNo;
    /**
     * 字段长度(64)：erp入库单号
     */
    @ImportExcelColumnProperty(desc = "字段长度(64)：erp入库单号")
    private String erpOrderNo;
    /**
     * 字段长度(32)：货主编码
     */
    @ImportExcelColumnProperty(desc = "字段长度(32)：货主编码")
    private String ownerCode;
    /**
     * 店铺id
     */
    @ImportExcelColumnProperty(desc = "店铺id")
    private Long shopId;
    /**
     * 字段长度(64)：原平台交易单号
     */
    @ImportExcelColumnProperty(desc = "字段长度(64)：原平台交易单号")
    private String bizOrderId;
    /**
     * 字段长度(64)：原物流单号
     */
    @ImportExcelColumnProperty(desc = "字段长度(64)：原物流单号")
    private String sExpressNo;
    /**
     * 字段长度(64)：原物流公司名
     */
    @ImportExcelColumnProperty(desc = "字段长度(64)：原物流公司名")
    private String sExpressName;
    /**
     * 字段长度(64)：退货物流单号
     */
    @ImportExcelColumnProperty(desc = "字段长度(64)：退货物流单号")
    private String rExpressNo;
    /**
     * 字段长度(64)：退货物流公司名
     */
    @ImportExcelColumnProperty(desc = "字段长度(64)：退货物流公司名")
    private String rExpressName;
    /**
     * 退货类型（0=部分退，1=全退）
     */
    @ImportExcelColumnProperty(desc = "退货类型（0=部分退，1=全退）")
    private Integer returnType;
    /**
     * 字段长度(16)：仓库编码
     */
    @ImportExcelColumnProperty(desc = "字段长度(16)：仓库编码")
    private String warehouseCode;
    /**
     * 字段长度(1024)：备注
     */
    @ImportExcelColumnProperty(desc = "字段长度(1024)：备注")
    private String remark;
    /**
     * 字段长度(2048)：扩展字段
     */
    @ImportExcelColumnProperty(desc = "字段长度(2048)：扩展字段")
    private String extend;
    /**
     * 明细
     */
    @ImportExcelColumnProperty(desc = "明细")
    private String  orderDetails;
    /**
     * 字段长度(16)：订单来源平台编码
     */
    @ImportExcelColumnProperty(desc = "字段长度(16)：订单来源平台编码")
    private String sourcePlatformCode;
    /**
     * 字段长度(16)：订单来源平台名称
     */
    @ImportExcelColumnProperty(desc = "字段长度(16)：订单来源平台名称")
    private String sourcePlatformName;
    /**
     * 销退类型  1: 拒收  2：拦截 3：退货 4：无头包裹
     */
    @ImportExcelColumnProperty(desc = "销退类型  1: 拒收  2：拦截 3：退货 4：无头包裹")
    private Integer saleReturnType;
    /**
     * 关联出库单信息
     */
    @ImportExcelColumnProperty(desc = "关联出库单信息")
    private String relatedOutboundInfo;

}
