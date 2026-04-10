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

    // 需要分发的命令
    private volatile FloorRequest requestTail = null;

    private volatile FloorRequest requestHead = null;


    private void dispatchRequest() {
        for(; ;) {
            if (requestHead == null) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
           while (requestHead != null) {
               FloorRequest request = requestHead;
               boolean deal = false;
               for (Elevator elevator : elevators) {
                   if (elevator.dutyRequest(request.targetFloor, request.moveForward)) {
                       deal = true;
                       break;
                   }
               }
               if (deal) {
                   requestHead = request.next;
               } else {
                   // 失败修改放到队尾
                   updateTailRequest(request);
               }
               request.next = null;
           }

        }
    }


    public synchronized void sendUpRequest(Integer floor) {
       if (requestTail == null) {
           FloorRequest request = new FloorRequest();
           request.moveForward = 1;
           request.targetFloor = floor;
           initFloor(request);
       } else {
           addFloorRequest(1, floor);
       }
    }

    private void initFloor(FloorRequest request) {
        requestTail = request;
        requestHead = request;
    }

    private void addFloorRequest(int moveForward, Integer floor) {
        FloorRequest request = new FloorRequest();
        request.moveForward = moveForward;
        request.targetFloor = floor;
        updateTailRequest(request);
    }

    private synchronized void updateTailRequest(FloorRequest request) {
        if (requestTail == null) {
            initFloor(request);
            return;
        }
        requestTail.next = request;
        requestTail = request;
    }

    public synchronized void sendDownRequest(Integer floor) {
        if (requestTail == null) {
            FloorRequest request = new FloorRequest();
            request.moveForward = -1;
            request.targetFloor = floor;
            initFloor(request);
        } else {
            addFloorRequest(-1, floor);
        }
    }


    @Getter
    public static class FloorRequest {
        FloorRequest next;
        Integer moveForward;
        Integer targetFloor;
    }
}
