package elevator;

import java.util.ArrayList;
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

    // 当前楼层
    private volatile Integer currentFloor;

    public volatile Floor[] toStopFloors;

    public volatile int stopCount;

    public volatile boolean empty;

    private final Object lock = new Object();

    public final int count;

    public Elevator(int count1) {
        toStopFloors = new Floor[count1];
        this.count = count1;
    }

    public boolean dutyRequest(Integer targetFloor, Integer moveForward, Floor floor) {
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
            toStopFloors[floor.floor - Building.MIN_FLOOR] = floor;
            increaseStopCount();
            if(empty) {
                synchronized (lock){
                    notify();
                }
            }
        }
        return addsucc;
    }

    private synchronized void increaseStopCount() {
        stopCount ++;
    }

    public void toRun() throws Exception {
        for (; ; ) {
            if (getStopCount() <= 0) {
                synchronized (lock) {
                    currentMoveForward = 0;
                    empty = true;
                    wait();
                }
            }
            if (currentMoveForward == 1) {
                for (int i = currentFloor; i <= Building.MAX_FLOOR; i++) {
                    currentFloor = i;
                    System.out.printf("运行方向:%s 当前楼层:%d", getForward(), currentFloor);
                    Floor floor = toStopFloors[i - Building.MIN_FLOOR];
                    if (floor != null) {
                        floor.peopleIn();
                        floor.peopleOut();
                        downStopCount();
                    }
                    // 模拟运行沉睡 1s;
                    Thread.sleep(1000);
                }
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

}
