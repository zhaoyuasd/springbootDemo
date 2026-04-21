package caom.laozao.springbootdemo.salableinventory;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: dongli
 * @since: 2026/4/21 13:56
 * @description:
 */

@Getter
@Setter
public class StockReport implements Serializable {

    private String storeCode;

    private String storeName;

    private String stockDate;

    private String productCode;

    private String productName;

    private String brand;

    private String basicUnit;

    private Integer basicBox;

    private String company;

    private String type;

    private Integer amount;

    private Integer unDelivery;

    private BigDecimal totalPrice;

    private Integer dcQude;

    private Integer dcNothe;
}

