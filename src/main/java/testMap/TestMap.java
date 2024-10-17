package testMap;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import org.apache.http.client.utils.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dongli
 * @create 2024/5/28 11:05
 * @desc
 */

public class TestMap {
    public static void main(String[] args) {
        Date date =DateUtil.parse("2024-12", "yyyy-MM");
        LocalDate beginDate = LocalDate.of(DateUtil.year(date), DateUtil.month(date)+1, 1);;
        System.out.println(beginDate);
        BigDecimal bg = new BigDecimal("0.43324234234");
        bg = bg.setScale(2, RoundingMode.UP);
        System.out.println(bg);
    }
}
