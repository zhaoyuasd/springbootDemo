package elevator;

import javax.jws.Oneway;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author dongli
 * @create 2023/7/9 16:04
 * @desc
 */

public class Elevator {

    /**
     * 运行状态
     */
    private volatile MoveState state;

    /**
     * 每个楼层是否有 命令
     */
    private final Integer[] floorOrder;

    private volatile Integer currentFloor;

    private Object lock = new Object();


    private ConcurrentMap<Integer, List<Person>> TO_ENTER = new ConcurrentHashMap<>();

    private ConcurrentMap<Integer, List<Person>> TO_OUTER = new ConcurrentHashMap<>();

    public Elevator(Integer floors) {
        floorOrder = new Integer[floors];
        state = MoveState.STILL;
    }

    public MoveState getState() {
        return state;
    }

    public void star() {
        new Thread(() -> {
            try {
                while (true) {
                    synchronized (lock) {
                        if (TO_ENTER.isEmpty() && TO_OUTER.isEmpty()) {
                            state = MoveState.STILL;
                            lock.wait();
                        }
                    }

                    Thread.sleep(1000);
                    if (MoveState.UP.equals(state)) {
                        currentFloor++;
                    } else {
                        currentFloor--;
                    }
                    if (Objects.nonNull(floorOrder[currentFloor])) {
                        TO_OUTER.getOrDefault(currentFloor, new ArrayList<>()).forEach(Person::toDoNetx);
                        TO_ENTER.getOrDefault(currentFloor, new ArrayList<>()).forEach(Person::toDoNetx);
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

}


    public void enterElevator(Person person, Integer toFloor) {
        List<Person> personList = TO_OUTER.computeIfAbsent(toFloor, k -> new ArrayList<>());
        personList.add(person);
        floorOrder[toFloor] = 1;
    }

    public void outerElevator(Person person, Integer toFloor) {
        List<Person> personList = TO_OUTER.computeIfAbsent(toFloor, k -> new ArrayList<>());
        personList.remove(person);
        floorOrder[toFloor] = 1;
    }

    public void addWillEnter(Integer currentFloor, Person person) {
        List<Person> personList = TO_OUTER.computeIfAbsent(currentFloor, k -> new ArrayList<>());
        personList.add(person);
        floorOrder[currentFloor] = 1;
    }
}
