import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author dongli
 * @create 2025/2/22 18:37
 * @desc
 */

public class TestSendUdp {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getByName("192.168.31.147");

        Integer i = 0;
        try (DatagramSocket socket = new DatagramSocket()) {
            // 解析目标IP地址
           while (true) {
               i = i + 20;
               String speed = i.toString();
               Thread.sleep(1500);
               byte[] buffer = speed.getBytes();
               // 创建DatagramPacket实例
               DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9981);

               // 发送数据包
               socket.send(packet);
               System.out.println("send over");
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
