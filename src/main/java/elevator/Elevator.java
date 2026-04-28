package elevator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author dongli
 * @create 2023/7/9 16:04
 * @desc
 */

public class Elevator {

    // -1 向下 0 停止  1 向上
    private volatile Integer currentMoveForward;

    private final List<Person> inEle = new ArrayList<>();

    // 当前楼层
    private volatile Integer currentFloor;

    private final Floor[] toStopFloors;

    private volatile int stopCount;

    private volatile boolean empty;

    private final Object lock = new Object();



    public Elevator(int count1) {
        toStopFloors = new Floor[count1];
        currentMoveForward = 0;
        currentFloor = 1;
        stopCount = 0;
    }

    public synchronized boolean dutyRequest(Integer targetFloor, Integer moveForward, Floor floor) {
        boolean addsucc = false;
        if (Objects.equals(currentMoveForward, moveForward)) {
            switch (currentMoveForward) {
                case -1:
                    if (targetFloor < currentFloor) {
                        addsucc = true;
                    }
                    break;
                case 1:
                    if (targetFloor > currentFloor) {
                        addsucc = true;
                    }
                    break;
                default:
                    break;
            }
        }
        if (currentMoveForward == 0) {
            currentMoveForward = floor.floor > currentFloor ? 1 : -1;

            addsucc = true;
        }
        if (addsucc) {
            System.out.println("接收任务：" + currentFloor + " -> " + targetFloor);
            toStopFloors[floor.floor - Building.MIN_FLOOR] = floor;
            increaseStopCount();
            if (empty) {
                synchronized (lock) {
                    lock.notify();
                    empty = false;
                }
            }
        }
        return addsucc;
    }

    private synchronized void increaseStopCount() {
        stopCount++;
    }

    public void toRun() {
        System.out.println("Elevator开始工作");
        LockCutDown.finish();
        for (; ; ) {
            if (getStopCount() <= 0) {
                synchronized (lock) {
                    currentMoveForward = 0;
                    empty = true;
                    System.out.println("任务执行完毕 进入休眠");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            for (int i = currentFloor;
                 getStopCount() > 0 && (currentMoveForward == 1 ? i < Building.MAX_FLOOR : i >= Building.MIN_FLOOR);
                 i = i + currentMoveForward) {
                doFloor(i);
                // 模拟运行沉睡 1s;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

    private synchronized void doFloor(int i) {
        currentFloor = i;
        System.out.printf("运行方向:%s 当前楼层:%d \n\r", getForward(), currentFloor);
        Floor floor = toStopFloors[i - Building.MIN_FLOOR];
        if (floor != null) {
            floor.peopleIn(this);
            peopleOut();
            downStopCount();
            toStopFloors[i - Building.MIN_FLOOR] = null;
        }
    }

    private void peopleOut() {
        Iterator<Person> iterator = inEle.iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (currentFloor.intValue() == p.getTargetFloor()) {
                System.out.println(p.getName() + "到达：" + currentFloor + " 出去了");
                iterator.remove();
            }
        }
    }

    private String getForward() {
        return currentMoveForward == 1 ? " 上行" : "下行";
    }

    private synchronized void downStopCount() {
        stopCount--;
    }

    private int getStopCount() {
        return stopCount;
    }

    public int getMoveForward() {
        return currentMoveForward;
    }

    public void peopleIn(Person people) {
        System.out.println(people.getName() + " 进入");
        inEle.add(people);
        people.sendRequestFromElevator();
    }

    public void start() {
        new Thread(this::toRun).start();
    }
}
