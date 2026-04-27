package elevator;


import java.util.ArrayList;
import java.util.List;

/**
 * @author: dongli
 * @since: 2026/4/10 10:03
 * @description: 楼层 可以按电梯
 */


public class Floor {

    public  final Integer floor;

    private final Building building;

    private List<Person> intoEle = new ArrayList<>();

    public Floor(Integer floor, Building building) {
        this.floor = floor;
        this.building = building;
    }


    public void goUp(Person person) {
        if (floor >= Building.MAX_FLOOR) {
            throw new RuntimeException("你要上天？");
        }
        building.sendUpRequest(floor, this);
        intoEle.add(person);
    }

    public void goDown() {
        if (floor <= Building.MIN_FLOOR) {
            throw new RuntimeException("你要下地？");
        }
        building.sendDownRequest(floor, this);

    }


}
