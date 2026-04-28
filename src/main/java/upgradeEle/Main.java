package upgradeEle;


import java.util.ArrayList;
import java.util.List;

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

        building.start();
        elevator.start();
        LockCutDown.toWait();

        System.out.println("人们开始进入。。。");
        List<Person> personList = new ArrayList<>();
//        ThreadLocalRandom random = ThreadLocalRandom.current();
//        for (int i = 1; i < 3 ; i++) {
//            int from = random.nextInt(-2, 35);
//            int to = random.nextInt(-2, 35);
//            if (from == to) {
//                continue;
//            }
//            System.out.println("from - > to :" + from + " -> " + to);
//            personList.add(new Person(from, to, from + "-> " + to, building));
//        }
        personList.add(new Person(7, 28, 7 + "-> " + 28, building));
        System.out.println("from - > to :" + 7 + " -> " + 28);
        personList.add(new Person(33, 20, 33 + "-> " + 20, building));
        System.out.println("from - > to :" + 33 + " -> " + 20);

        personList.add(new Person(35, -2, 35 + "-> " + -2, building));
        System.out.println("from - > to :" + 35 + " -> " + -2);

        for (Person person: personList) {
            person.sendRequestFromFloor(Floor.floors[person.getCurrentFloor() - Building.MIN_FLOOR]);
        }

    }

}
