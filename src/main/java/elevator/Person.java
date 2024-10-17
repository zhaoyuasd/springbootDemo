package elevator;

/**
 * @author dongli
 * @create 2023/7/9 16:24
 * @desc
 */

public class Person {
    private Integer currentFloor;

    private Integer toFloor;

    private MoveState state;

    private Building building;

    private Elevator elevator;


    private final Object lock = new Object();

    public Person(Integer currentFloor, MoveState state, Integer toFloor, Building building) {
        this.currentFloor = currentFloor;
        this.state = state;
        this.toFloor = toFloor;
        this.building = building;
    }

    public void sendOrder() {
        elevator = building.dealOrder(this, currentFloor, state);
    }

    public void enterElevator() {
        elevator.enterElevator(this, toFloor);
    }

    public void outerElevator() {
        elevator.outerElevator(this, toFloor);
    }

    public void move() {
        new Thread(() -> {
            synchronized (lock) {
                try {
                    sendOrder();
                    lock.wait();
                    enterElevator();
                    lock.wait();
                    outerElevator();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }

    public void  toDoNetx() {
        synchronized (lock) {
            lock.notify();
        }
    }

}
