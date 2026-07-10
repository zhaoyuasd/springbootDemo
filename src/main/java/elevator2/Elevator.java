package elevator2;


import java.util.*;

/**
 * @author: dongli
 * @since: 2026/7/9 14:34
 * @description:
 */

public class Elevator extends Thread {

    private volatile Map<Integer, Floor> peopleIn = new HashMap<>();

    private volatile List<Integer> orderList = new ArrayList<>();

    private volatile Map<Integer, List<Person>> peopleOut = new HashMap<>();

    private volatile Floor currentFloor;

    public Elevator(Floor floor) {
        currentFloor = floor;
        runForward = 0;
    }

    private volatile Integer runForward;

    public void openDoor(Integer fl) {
        synchronized (orderList) {
            if (peopleIn.containsKey(fl)) {
                Floor floor = peopleIn.remove(fl);
                floor.waitPerson.forEach(e -> e.enterElevator(this));
            }
            if (peopleOut.containsKey(fl)) {
                List<Person> pp = peopleOut.remove(fl);
                pp.forEach(Person::outElevator);
            }
            orderList.remove(fl);
        }
    }


    public void run() {
        System.out.println("当前楼层 ：" + currentFloor.current);
        while (true) {
            if (orderList.isEmpty()) {
                runForward = 0;
                System.out.println("为空 休眠");
                SleepUtils.sleep(500L);
            } else {
                if (isStill()) {
                    Integer nextFloor = fetchNextFloor();
                    setRunForward(nextFloor);
                }
                SleepUtils.sleep(500L); // 模拟运行
                updateCurrentFloor(); // 更新楼层
                doEvent(); // 到达楼层后处理后续事件
            }

        }
    }

    private void doEvent() {
        Integer fl = currentFloor.current;
        if (peopleIn.containsKey(fl) || peopleOut.containsKey(fl)) {
            openDoor(fl);
        }
        if (currentFloor.current == Building.MAX_FLOOR.intValue()) {
            runForward = 0;
            Collections.sort(orderList);
            Collections.reverse(orderList);
        }
        if (currentFloor.current == Building.MIN_FLOOR.intValue()) {
            runForward = 0;
            Collections.sort(orderList);
        }

        if (orderList.isEmpty()) {
            setStill();
            return;
        }
        Integer nextFloor = fetchNextFloor();
        if (nextFloor > currentFloor.current && isDown()) {
            runForward = 0;
        }

        if (nextFloor < currentFloor.current && isUp()) {
            runForward = 0;
        }
    }

    private void setStill() {
        synchronized (orderList) {
            runForward = 0;
        }
    }

    private void updateCurrentFloor() {
        int fl = currentFloor.current;
        fl = fl + runForward;
        if (fl == 0) {
            if (isUp()) {
                fl = 1;
            } else {
                fl = -1;
            }
        }
        currentFloor = Building.getFloor(fl);
        System.out.println("当前楼层 ：" + currentFloor.current);
    }


    private void setRunForward(Integer nextFloor) {
        if (nextFloor > currentFloor.current) {
            runForward = 1;
        } else {
            runForward = -1;
        }
    }

    private Integer fetchNextFloor() {
        synchronized (orderList) {
            return orderList.get(0);
        }
    }

    private boolean isDown() {
        return runForward == -1;
    }

    private boolean isUp() {
        return runForward == 1;
    }

    private boolean isStill() {
        return runForward == 0;
    }

    public boolean canDealDownOrder(Floor floor) {
        if ((isDown() && currentFloor.current > floor.current) || isStill()) {
            System.out.println("接收 下 命令 ->" + floor.current);
            addFoolerOrder(floor);
            return true;
        }
        return false;
    }

    public boolean canDealUpOrder(Floor floor) {
        if ((isUp() && currentFloor.current < floor.current) || isStill()) {
            System.out.println("接收 上 命令 ->" + floor.current);
            addFoolerOrder(floor);
            return true;
        }
        return false;
    }

    private void addFoolerOrder(Floor floor) {
        synchronized (orderList) {
            peopleIn.put(floor.current, floor);
            addToOrderList(floor.current);
        }
    }

    private void addToOrderList(Integer fl) {
        if (!orderList.contains(fl)) {
            orderList.add(fl);
            Collections.sort(orderList);
            if (isDown()) {
                Collections.reverse(orderList);
            }
        }
    }

    public void addPeopleAndOrder(Person person) {
        synchronized (orderList) {
            int fl = person.target.current;
            List<Person> pp = peopleOut.computeIfAbsent(person.target.current, k -> new ArrayList<>());
            pp.add(person);
            addToOrderList(fl);
        }
    }
}
