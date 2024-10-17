package util;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author dongli
 * @create 2023/7/18 13:22
 * @desc 外部服务订单 推送数据落库
 */

@Getter
@Setter
public class OuterOrderLog {


    private Long id;

    @ImportExcelColumnProperty(desc = "外部推送单号")
    private String outerCode;

    @ImportExcelColumnProperty(desc = "E管家订单号")
    private String refCode;

    @ImportExcelColumnProperty(desc = "执行状态")
    private Integer status;

    @ImportExcelColumnProperty(desc = "报文原始内容")
    private String content;

    @ImportExcelColumnProperty(desc = "备注")
    private String remark;

    @ImportExcelColumnProperty(desc = "品牌")
    private String brandId;

    private Date createTime;
}
