package elevator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongli
 * @create 2023/7/9 16:31
 * @desc
 */

public class Building {

    // 最低楼 只能往下
    public final static Integer MAX_FLOOR = 36;

    // 最高楼  只能往上
    public final static Integer MIN_FLOOR = -2;

    private final List<Elevator> elevators = new ArrayList<>();

    private Floor[] floors;


    private MyQueue<FloorRequest> queue = new MyQueue<>();

    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
    }

    public void setFloors(Floor[] floors) {
        this.floors = floors;
    }

    private void dispatchDownRequest() {
      for (;;) {
          try {
              FloorRequest request = queue.popTask();
              if (!dutyRequest(request, elevators)) {
                  queue.addTask(request);
              }
          } catch (Exception e) {
              throw new RuntimeException(e);
          }
      }
    }

    private boolean dutyRequest(FloorRequest request, List<Elevator> elevators) {
        for (Elevator elevator : elevators) {
            if (elevator.dutyRequest(request.targetFloor, request.moveForward, request.getFloor())) {
                return true;
            }
        }
        return false;
    }


    public  void sendUpRequest(Integer floor, Floor flo) {
        FloorRequest request = new FloorRequest();
        request.moveForward = 1;
        request.targetFloor = floor;
        request.floor = flo;
        queue.addTask(request);
    }






    public  void sendDownRequest(Integer floor, Floor flo) {
        FloorRequest request = new FloorRequest();
        request.moveForward = -1;
        request.targetFloor = floor;
        request.floor = flo;
        queue.addTask( request);
    }




    @Getter
    public static class FloorRequest {
        FloorRequest next;
        Integer moveForward;
        Integer targetFloor;
        Floor floor;
    }
}
