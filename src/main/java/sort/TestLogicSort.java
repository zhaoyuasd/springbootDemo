package sort;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MapUtils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2024/10/28 16:45
 * @desc
 */

public class TestLogicSort {
    public static void main(String[] args) {
        Integer[] original = {1, 2, 3, 4, 5, 6};
        Integer[] sort1 = {6, 1, 3};
        Integer[] sort2 = {2, 5, 4, 1, 6, 3};
        Integer[] sort3 = {4, 1};
        sortLogic(original, sort1, sort2, sort3);
    }

    private static void sortLogic(Object[] original, Object[]... sort) {
        List<Node> initNode = Arrays.stream(original).map(Node::new).collect(Collectors.toList());
        Map<Object, Node> value2Node = initNode.stream().collect(Collectors.toMap(Node::getValue, e -> e));
        Node header = null;
        for (Object[] obts : sort) {
            if (header == null) {
                header = value2Node.get(obts[0]);
            }
            Node tmpHeader = value2Node.get(obts[0]);

            for (int index = 1; index < obts.length; index++) {
                Node current = value2Node.get(obts[index]);
                Node currentPre = current.pre;
                if (currentPre != null) {
                    Node tmpHeaderHeader = tmpHeader.getHeader();
                    currentPre.next = tmpHeaderHeader;
                    tmpHeaderHeader.pre = currentPre;
                }
                tmpHeader.next = current;
                current.pre = tmpHeader;
                tmpHeader = current;
            }
        }
        while (true) {
            while (header != null) {
                value2Node.remove(header.getValue());
                System.out.print(header.getValue().toString() + " ");
                header = header.next;
            }
            if (MapUtils.isNotEmpty(value2Node)) {
                header = new ArrayList<Node>(value2Node.values()).get(0);
            } else {
                break;
            }
        }
    }

    @Getter
    @Setter
    static class Node {
        Node pre, next;
        Object value;

        Node(Object value) {
            this.value = value;
        }

        public Node getHeader() {
            Node preHeader = this.pre;
            while (preHeader.pre != null) {
                preHeader = preHeader.pre;
            }
            return preHeader;
        }
    }
}
