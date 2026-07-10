package elevator2;


import java.util.ArrayList;
import java.util.List;

/**
 * @author: dongli
 * @since: 2026/7/9 14:34
 * @description:
 */

public class Floor {
    public final Integer current;

    public final List<Person> waitPerson = new ArrayList<>();


    public Floor(Integer current) {
        this.current = current;
    }

    public void pushDown(Person person) {
        waitPerson.add(person);
        Building.receiveDownOrder(this);
    }

    public void pushUp(Person person) {
        waitPerson.add(person);
        Building.receiveUpOrder(this);
    }
}
