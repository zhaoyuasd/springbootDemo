package elevator2;


/**
 * @author: dongli
 * @since: 2026/7/9 14:34
 * @description:
 */

public class Person {

    private final Floor current;

    public final Floor target;

    private final String name;

    public Person(Floor current, Floor target, String name) {
        this.current = current;
        this.target = target;
        this.name = name;
    }


    public void pushFloorButton() {
        if (current.current > target.current) {
            current.pushDown(this);
        } else {
            current.pushUp(this);
        }
    }

    public void enterElevator(Elevator elevator) {
        System.out.println(name + " enterElevator");
        elevator.addPeopleAndOrder(this);
    }


    public void outElevator() {
        System.out.println(name + " outElevator");
    }
}
