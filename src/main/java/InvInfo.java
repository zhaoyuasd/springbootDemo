import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author dongli
 * @create 2025/7/1 17:31
 * @desc
 */
@Getter
@Setter
public class InvInfo {

    private Long id;

    private Long stockId;

    private String stockCode;

    private String stockName;

    private Long skuId = -1L;

    private String skuNo;

    private String productNo;

    private Integer freezeQty;

    private String  createTime = DateUtil.formatDateTime(new Date());
    /**
     * 统计时间
     */
    private String countTime = "2025-06-30";

    private String brandId = "friso";

    @Override
    public String toString() {
        return "(" +id + "," +
                stockId + "," +
               "'"+ stockCode + "'," +
                  stockName + "," +
                skuId + "," +
                "'"+   skuNo + "'," +
                productNo + "," +
                freezeQty + "," +
                "'"+    createTime + "'," +
                "'"+  countTime + "'," +
                "'" +brandId + "' ),";
    }
}
