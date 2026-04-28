package upgradeEle;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: dongli
 * @since: 2026/4/10 10:03
 * @description: 楼层 可以按电梯
 */


public class Floor {

  public static final  Floor[] floors = new Floor[Building.MAX_FLOOR - Building.MIN_FLOOR];

  static {
      for (int i = Building.MIN_FLOOR; i < Building.MAX_FLOOR; i++) {
          floors[i - Building.MIN_FLOOR] = new Floor(i);
      }
  }

    public final Integer floor;

    private final List<Person> intoUpEle = new ArrayList<>();

    private final List<Person> intoDownEle = new ArrayList<>();

    private volatile boolean needOpen = false;


    public Floor(Integer floor) {
        this.floor = floor;
    }


    public synchronized void goUp(Person person) {
        if (floor >= Building.MAX_FLOOR) {
            throw new RuntimeException("你要上天？");
        }
        intoUpEle.add(person);
        needOpen = true;
    }

    public synchronized  void goDown(Person person) {
        if (floor <= Building.MIN_FLOOR) {
            throw new RuntimeException("你要下地？");
        }
        intoDownEle.add(person);
        needOpen = true;
    }


}
