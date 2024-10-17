package elevator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongli
 * @create 2023/7/9 16:31
 * @desc
 */

public class Building {

   private  List<Elevator> elevators = new ArrayList<>();

   public void addElevator(Elevator elevator) {
       elevators.add(elevator);
   }

    public Elevator dealOrder(Person person, Integer currentFloor, MoveState state) {
        Elevator elevator = calculateElevator(state, person);
        elevator.addWillEnter(currentFloor, person);
        return elevator;
    }

    private Elevator calculateElevator(MoveState state, Person person) {
       return elevators.get(0);
    }
}
