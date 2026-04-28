package elevator;

/**
 * @author dongli
 * @create 2023/7/9 16:24
 * @desc
 */

public class Person {

   private Integer currentFloor;

   private Integer targetFloor;

   private String name;

   private Building building;

    public Person(Integer currentFloor, Integer targetFloor, String name, Building building) {
        this.currentFloor = currentFloor;
        this.targetFloor = targetFloor;
        this.name = name;
        this.building = building;
    }


    public Integer getTargetFloor() {
        return targetFloor;
    }


    public String getName() {
        return name;
    }


    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public void sendRequestFromFloor(Floor floor) {
        if (currentFloor > targetFloor) {
            floor.goDown(this);
        } else {
            floor.goUp(this);
        }
    }

    public void sendRequestFromElevator() {
        if (currentFloor > targetFloor) {
            building.sendDownRequest(targetFloor);
        } else {
            building.sendUpRequest(targetFloor);
        }
    }
}
