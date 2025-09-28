package test;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import org.dromara.hutool.core.date.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dongli
 * @create 2025/9/25 16:37
 * @desc
 */

public class TestSortDate {
    public static void main(String[] args) {
        List<DateInv> list = new ArrayList<>();

        Date date = new Date();
        list.add(new DateInv(13, DateUtil.offsetDay(date, 12)));
        list.add(new DateInv(25, DateUtil.offsetDay(date, 2)));
        list.add(new DateInv(16, null));
        list.add(new DateInv(23, null));
        list.sort((a, b) -> {
            if (a.getExpireDate() == b.getExpireDate() || (a.getExpireDate() == null && b.getExpireDate() == null)) {
                return 0;
            }
            if (a.getExpireDate() != null && b.getExpireDate() != null) {
                return a.getExpireDate().compareTo(b.getExpireDate());
            } else if (a.getExpireDate() == null) {
                return -1;
            } else {
                return 1;
            }
        });

        System.out.println(JSON.toJSONString(list));
    }

    @Getter
    private static class DateInv {
        Integer qty;
        Date  expireDate;

        public DateInv(Integer qty, Date expireDate) {
            this.qty = qty;
            this.expireDate = expireDate;
        }
    }
}
