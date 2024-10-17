package util;


import java.util.Date;

/**
 * @author dongli
 * @create 2023/6/7 16:31
 * @desc 抖音超市 新建的发货单
 */

public class TiktokOutboundOrder {

    private Long id;

    /**
     * 字段长度(16):仓编码
     */
    @ImportExcelColumnProperty(desc = "仓编码")
    private String warehouseCode;

    /**
     * 字段长度(16):入库仓编码，order_type为DB时必填
     */
    @ImportExcelColumnProperty(desc = "字段长度(16):入库仓编码，order_type为DB时必填")
    private String inWarehouseCode;
    /**
     * 物流-1；快递-2；自提-3
     */
    @ImportExcelColumnProperty(desc = "物流-1；快递-2；自提-3")
    private Long deliveryMode;
    /**
     * 字段长度(32):货主编码
     */
    @ImportExcelColumnProperty(desc = "字段长度(32):货主编码")
    private String ownerCode;
    /**
     * 店铺ID
     */
    @ImportExcelColumnProperty(desc = "店铺ID")
    private Long shopId;
    /**
     * 字段长度(64):出库单号
     */
    @ImportExcelColumnProperty(desc = "字段长度(64):出库单号")
    private String outboundOrderNo;
    /**
     * 字段长度(64):平台交易单号，toC才有此参数
     */
    @ImportExcelColumnProperty(desc = "字段长度(64):平台交易单号，toC才有此参数")
    private String platformOrderNo;
    /**
     * 字段长度(64):erp出库单号
     */
    @ImportExcelColumnProperty(desc = "字段长度(64):erp出库单号")
    private String erpOrderNo;
    /**
     * 销售出库= 1 调拨出库= 2 退仓出库= 3
     */
    @ImportExcelColumnProperty(desc = "销售出库= 1 调拨出库= 2 退仓出库= 3")
    private Long orderType;
    /**
     * 应发数量
     */
    @ImportExcelColumnProperty(desc = "应发数量")
    private Long expectedQty;
    /**
     * 字段长度(16):订单来源平台编码
     */
    @ImportExcelColumnProperty(desc = "字段长度(16):订单来源平台编码")
    private String sourcePlatformCode;
    /**
     * 字段长度(16):订单来源平台名称
     */
    @ImportExcelColumnProperty(desc = "字段长度(16):订单来源平台名称")
    private String sourcePlatformName;
    /**
     * 前台订单下单时间
     */
    @ImportExcelColumnProperty(desc = "前台订单下单时间")
    private Date orderTime;
    /**
     * 审单时间
     */
    @ImportExcelColumnProperty(desc = "审单时间")
    private Date reviewTime;
    /**
     * 字段长度(64):快递公司编码
     */
    @ImportExcelColumnProperty(desc = "字段长度(64):快递公司编码")
    private String expressCode;
    /**
     * 字段长度(64):快递公司编码
     */
    @ImportExcelColumnProperty(desc = "字段长度(64):快递公司编码")
    private String expressName;
    /**
     * 是否保价 1=是 0=否
     */
    @ImportExcelColumnProperty(desc = "是否保价 1=是 0=否")
    private Long isInsure;
    /**
     * 保价金额
     */
    @ImportExcelColumnProperty(desc = "保价金额")
    private Long insureAmount;
    /**
     * 字段长度(64):快递产品类型
     */
    @ImportExcelColumnProperty(desc = "字段长度(64):快递产品类型")
    private String expressProductId;
    /**
     * 字段长度(128):快递产品名称
     */
    @ImportExcelColumnProperty(desc = "字段长度(128):快递产品名称")
    private String expressProductName;
    /**
     * 是否允许合单 1=是 0=否
     */
    @ImportExcelColumnProperty(desc = "是否允许合单 1=是 0=否")
    private Long allowMerge;
    /**
     * 字段长度(500):备注
     */
    @ImportExcelColumnProperty(desc = "字段长度(500):备注")
    private String remark;
    /**
     * 字段长度(500):买家备注
     */
    @ImportExcelColumnProperty(desc = "字段长度(500):买家备注")
    private String buyerMessage;
    /**
     * 字段长度(500):卖家备注
     */
    @ImportExcelColumnProperty(desc = "字段长度(500):卖家备注")
    private String sellerMessage;
    /**
     * extend
     */
    @ImportExcelColumnProperty(desc = "扩展信息")
    private String extend;
    /**
     * 发送者
     */
    @ImportExcelColumnProperty(desc = "发送信息")
    private String senderInfo;
    /**
     * 接收者
     */
    @ImportExcelColumnProperty(desc = "收件人信息")
    private String receiverInfo;
    /**
     * 订单明细
     */
    @ImportExcelColumnProperty(desc = "订单明细")
    private String details;
    /**
     * 字段长度(200):店铺名称
     */
    @ImportExcelColumnProperty(desc = "字段长度(200):店铺名称")
    private String shopName;
    /**
     * 期望送达时间，20220715增加
     */
    @ImportExcelColumnProperty(desc = "期望送达时间，20220715增加")
    private Date expectedDeliveredTime;
    /**
     * 期望出库时间，20220715增加
     */
    @ImportExcelColumnProperty(desc = "期望出库时间，20220715增加")
    private Date expectedOutboundTime;
    /**
     * 期望揽收时间，20220715增加
     */
    @ImportExcelColumnProperty(desc = "期望揽收时间，20220715增加")
    private Date expectedCollectTime;
    /**
     * 字段长度(64):物流产品类型，20220715增加
     */
    @ImportExcelColumnProperty(desc = "字段长度(64):物流产品类型，20220715增加")
    private String expressProductType;
    /**
     * 增值服务，20220715增加
     */
    @ImportExcelColumnProperty(desc = "增值服务")
    private String valueAdded;
    /**
     * 是否为高值品，0=否；1=是，默认为0
     */
    @ImportExcelColumnProperty(desc = "是否为高值品，0=否；1=是，默认为0")
    private Long isHighValue;
    /**
     * 字段长度(64):物流产品类型名称，20230103增加
     */
    @ImportExcelColumnProperty(desc = "字段长度(64):物流产品类型名称，20230103增加")
    private String expressProductTypeName;
    /**
     * 供应商编码
     */
    @ImportExcelColumnProperty(desc = "供应商编码")
    private String supplierCode;
    /**
     * 供应商名称
     */
    @ImportExcelColumnProperty(desc = "供应商名称")
    private String supplierName;

}
