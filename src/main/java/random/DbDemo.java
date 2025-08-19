package random;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongli
 * @create 2025/8/19 13:50
 * @desc
 */

@Setter
@Getter
@AllArgsConstructor

public class DbDemo {
      int currentCount;
      int fromIndex;
      int endIndex;
      List<Integer> list;

      @Override
      public String toString() {
            list.sort(Integer::compareTo);
            return "DbDemo{" +
                    "endIndex=" + endIndex +
                    ", fromIndex=" + fromIndex +
                    ", currentCount=" + currentCount +
                    ", min = " + list.get(0) + ", max = " + list.get(list.size()-1) +
                    '}' ;
      }
}
