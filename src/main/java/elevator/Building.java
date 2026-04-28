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

    private MyQueue<FloorRequest> queueDispatch = new MyQueue<>();

    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
    }

    private void postTask() {
        System.out.println("Buding开始分发工作");
        LockCutDown.finish();

        for (;;) {
            try {
                FloorRequest request = queueDispatch.popTask();
                if (!dutyRequest(request, elevators)) {
                    // 失败重新入队
                    queue.addTask(request);
                }
                Thread.sleep(500);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void dispatchDownRequest() {
      System.out.println("Buding开始 投递 工作");
        LockCutDown.finish();
      for (;;) {
          try {
              queueDispatch.addTask(queue.popTask());
              Thread.sleep(500);
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

    public void sendDownRequest(Integer targetFloor) {
        FloorRequest request = new FloorRequest();
        request.moveForward = -1;
        request.targetFloor = targetFloor;
        request.floor = floors[targetFloor - MIN_FLOOR];
        queue.addTask( request);
    }

    public void sendDownRequest(Integer floor, Floor flo) {
        FloorRequest request = new FloorRequest();
        request.moveForward = -1;
        request.targetFloor = floor;
        request.floor = flo;
        queue.addTask( request);
    }


    public void sendUpRequest(Integer targetFloor) {
        FloorRequest request = new FloorRequest();
        request.moveForward = 1;
        request.targetFloor = targetFloor;
        request.floor = floors[targetFloor - MIN_FLOOR];
        queue.addTask(request);
    }


    public void start() {
        new Thread(this::dispatchDownRequest).start();
        new Thread(this::postTask).start();
    }



    public void setFloors(Floor[] floors) {
        this.floors = floors;
    }



    @Getter
    public static class FloorRequest {
        FloorRequest next;
        Integer moveForward;
        Integer targetFloor;
        Floor floor;
    }
}
