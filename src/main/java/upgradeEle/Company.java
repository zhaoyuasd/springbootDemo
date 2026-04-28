package upgradeEle;


import lombok.Getter;

/**
 * @author: dongli
 * @since: 2026/4/10 9:52
 * @description: 公司
 */

@Getter
public class Company {
    // 所属楼层
    private final Integer floor;

    public Company(Integer floor) {
        this.floor = floor;
    }

}
