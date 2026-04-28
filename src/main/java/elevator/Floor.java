package elevator;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: dongli
 * @since: 2026/4/10 10:03
 * @description: 楼层 可以按电梯
 */


public class Floor {

    public final Integer floor;

    private final Building building;

    private List<Person> intoUpEle = new ArrayList<>();

    private List<Person> intoDownEle = new ArrayList<>();

    public Floor(Integer floor, Building building) {
        this.floor = floor;
        this.building = building;
    }


    public void goUp(Person person) {
        if (floor >= Building.MAX_FLOOR) {
            throw new RuntimeException("你要上天？");
        }
        building.sendUpRequest(floor, this);
        intoUpEle.add(person);
    }

    public void goDown(Person person) {
        if (floor <= Building.MIN_FLOOR) {
            throw new RuntimeException("你要下地？");
        }
        building.sendDownRequest(floor, this);
        intoDownEle.add(person);
    }


    public void peopleIn(Elevator elevator) {

        // 底层或者顶层 人都是直接进入
        if (floor.intValue() == Building.MIN_FLOOR || floor == Building.MAX_FLOOR - 1) {
            intoDownEle.forEach(elevator::peopleIn);
            intoDownEle.clear();
            intoUpEle.forEach(elevator::peopleIn);
            intoUpEle.clear();
            return;
        }

        Iterator<Person> it ;
        if (elevator.getMoveForward() == -1) {
            it = intoDownEle.iterator();
            while (it.hasNext()) {
                Person p = it.next();
                it.remove();
                elevator.peopleIn(p);
            }
        } else {
            it = intoUpEle.iterator();
            while (it.hasNext()) {
                Person p = it.next();
                it.remove();
                elevator.peopleIn(p);
            }
        }




    }

}
