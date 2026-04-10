package elevator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author dongli
 * @create 2023/7/9 16:04
 * @desc
 */

public class Elevator {

  // -1 向下 0 停止  1 向上
  private Integer moveForward;

  // 当前楼层
  private volatile Integer currentFloor;

  // 后续需要停靠的楼层
  private List<Integer> nextStop;


  public boolean dutyRequest(Integer targetFloor, Integer moveForward) {
    return false;
  }
}
