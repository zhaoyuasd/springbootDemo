package excelTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author dongli
 * @create 2026/1/26 17:22
 * @desc
 */

@Data
@AllArgsConstructor
public class DeliveryRow {
    private String channel;
    private String carrier;
    private int totalOrderCnt;
    private String totalFulfillRate;

    Map<String, Detail> detail;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail{
        private int rangeOrderCnt;
        private String rangeRate;
        private double acceptMinutes;
        private double deliverMinutes;
        private String rangeFulfillRate;
        private double fee;
    }
}

