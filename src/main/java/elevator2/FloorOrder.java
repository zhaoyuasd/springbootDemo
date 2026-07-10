package elevator2;


/**
 * @author: dongli
 * @since: 2026/7/9 15:12
 * @description:
 */

public class FloorOrder {
    public final Integer runForward;
    public final Floor floor;

    public FloorOrder(Integer runForward, Floor floor) {
        this.runForward = runForward;
        this.floor = floor;
    }
}
