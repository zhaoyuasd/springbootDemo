package elevator;


/**
 * @author: dongli
 * @since: 2026/4/27 17:51
 * @description:
 */

public class Main {

    public static void main(String[] args) {
        Building building = new Building();
        Elevator elevator = new Elevator(Building.MAX_FLOOR - Building.MIN_FLOOR);
        building.addElevator(elevator);
        Floor[] floors = new Floor[Building.MAX_FLOOR - Building.MIN_FLOOR];
        for (int i = Building.MAX_FLOOR; i<= Building.MIN_FLOOR; i ++) {
            floors[i - Building.MAX_FLOOR] = new Floor(i, building);
        }
        building.setFloors(floors);


    }

}
