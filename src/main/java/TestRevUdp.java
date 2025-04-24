
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author dongli
 * @create 2025/2/14 10:27
 * @desc
 */

public class TestRevUdp {

    public static volatile  CarStatus status = new CarStatus();

    private final static BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(500);
    private final static Executor executor = new ThreadPoolExecutor(
            1,
            20,
            3,
            TimeUnit.MINUTES,
            queue,
            (r, executor) -> {
                try {
                    queue.put(r);
                } catch (InterruptedException e) {
                   e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
    );

    private volatile static Integer currentSpeed = 0;

    private static Integer port = 9983;

    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getByName("172.16.92.134");
        DatagramSocket socket = null;
        try {
            // 创建一个DatagramSocket实例，它将监听所有可用的网络接口上的默认UDP端口（可以指定端口号）
            socket = new DatagramSocket(9876); // 这里的9876是示例端口号，你可以根据需要更改

            byte[] bufferTmp = new byte[1024]; // 创建一个缓冲区来存储接收到的数据
            DatagramPacket tmpPacket = new DatagramPacket(bufferTmp, bufferTmp.length);

            System.out.println("UDP接收器已启动，等待数据包...");

            while (true) {
                // 接收数据包
                socket.receive(tmpPacket);

                // 获取发送方的IP地址和端口号
                InetAddress senderAddress = tmpPacket.getAddress();
                int senderPort = tmpPacket.getPort();

                // 获取接收到的数据
                byte[] data = tmpPacket.getData();
                int length = tmpPacket.getLength();

                // 将字节数据转换为字符串（注意：这里假设数据是文本）
               int cmd = (int) data[0];
                // 输出接收到的数据
                System.out.println("从IP地址 " + senderAddress.getHostAddress() + " 和端口 " + senderPort + " 接收到的数据: " + cmd);

                if (cmd == 10) {
                    System.out.println("汽车准备启动");
                    if (status.start) {
                        continue;
                    }
                    status.start = true;
                    executor.execute(() -> {
                        try (DatagramSocket startSocket = new DatagramSocket()) {
                            // 解析目标IP地址
                            while (!status.stop) {
                                currentSpeed = currentSpeed + 20;
                                String speed = currentSpeed.toString();
                                Thread.sleep(1500);
                                byte[] buffer = speed.getBytes();
                                // 创建DatagramPacket实例
                                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

                                // 发送数据包
                                startSocket.send(packet);
                                if (currentSpeed > 300) {
                                    status.start = false;
                                    return;
                                }
                            }
                            status.start = false;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                if (cmd == 20) {
                    System.out.println("汽车准备停止");
                    if (status.stop ) {
                        continue;
                    }
                    status.stop = true;
                    executor.execute(() -> {
                        try (DatagramSocket stopSocket = new DatagramSocket()) {
                            // 解析目标IP地址
                            while (true) {
                                currentSpeed = currentSpeed - 20;
                                String speed = currentSpeed.toString();
                                Thread.sleep(1500);
                                byte[] buffer = speed.getBytes();
                                // 创建DatagramPacket实例
                                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

                                // 发送数据包
                                stopSocket.send(packet);
                                if (currentSpeed <= 0) {
                                    status.stop = false;
                                    return;
                                }
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                if (cmd == 30) {
                    System.out.println("汽车已发射 结束");
                    return;
                }


                // 重置DatagramPacket的长度，以便下次接收
                tmpPacket.setLength(bufferTmp.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭socket（如果已打开）
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}

class CarStatus {
    public volatile   Boolean start = false;

    public volatile Boolean stop = false;
}