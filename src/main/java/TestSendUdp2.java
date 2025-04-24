import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author dongli
 * @create 2025/2/23 11:00
 * @desc
 */

public class TestSendUdp2 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getByName("192.168.31.42");
        int msg = 20;
        // Integer i = 0;
        try (DatagramSocket socket = new DatagramSocket()) {
            // 解析目标IP地址

                byte[] buffer = new byte[1];
                buffer[0] = (byte) msg;
                // i = i + 20;
                // String speed = i.toString();
                Thread.sleep(1500);
                // byte[] buffer = speed.getBytes();
                // 创建DatagramPacket实例
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9876);

                // 发送数据包
                socket.send(packet);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
