package elevator2;


import java.util.*;

/**
 * @author: dongli
 * @since: 2026/7/9 14:33
 * @description:
 */

public class Building {

    public static final List<FloorOrder> ORDERS = new ArrayList<>();
    public static final Integer MAX_FLOOR = 25;
    public static final Integer MIN_FLOOR = -3;
    public static Map<Integer, Floor> MAP = new HashMap<>();
    static {
        for(Integer i = -3; i < 0; i++) {
            MAP.put(i, new Floor(i));
        }
        for(Integer i = 1 ; i < 25; i++) {
            MAP.put(i, new Floor(i));
        }
    }
    public static Elevator elevator =  new Elevator(getFloor(1));
    public static Floor getFloor(Integer i) {
        return MAP.get(i);
    }

    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person(getFloor(-3), getFloor(5), "-3_5"));

        people.add(new Person(getFloor(18), getFloor(9), "18_9"));

        people.add(new Person(getFloor(7), getFloor(15), "7_15"));

        people.add(new Person(getFloor(20), getFloor(4), "20_4"));

        people.add(new Person(getFloor(20), getFloor(4), "20_4"));

        people.add(new Person(getFloor(23), getFloor(-2), "23_-2"));

        people.add(new Person(getFloor(23), getFloor(-2), "4_-2"));

        elevator.start();

        people.forEach(e -> {
            e.pushFloorButton();
            SleepUtils.sleep(1000L);
        });

       new Thread(Building::dispatchOrder).start();
    }


    public static void receiveDownOrder(Floor floor) {
        if (!elevator.canDealDownOrder(floor)) {
             addWaitOrder(new FloorOrder(-1, floor));
        }
    }

    public static void receiveUpOrder(Floor floor) {
        if (!elevator.canDealUpOrder(floor)) {
            addWaitOrder(new FloorOrder(1, floor));
        }
    }

    private  static void addWaitOrder(FloorOrder floorOrder) {
        synchronized (ORDERS) {
            ORDERS.add(floorOrder);
        }
    }

    public static void dispatchOrder()  {
        while (true) {
            if (ORDERS.isEmpty()) {
                SleepUtils.sleep(2000L);
            }
            synchronized (ORDERS) {
               Iterator<FloorOrder> it = ORDERS.iterator();
               while (it.hasNext()) {
                   FloorOrder order = it.next();
                   if (order.runForward == 1 && elevator.canDealUpOrder(order.floor)) {
                       it.remove();
                   }
                   if (order.runForward == -1 && elevator.canDealDownOrder(order.floor)) {
                       it.remove();
                   }
               }
            }
        }
    }
}
