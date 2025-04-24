package rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author dongli
 * @create 2025/4/24 10:15
 * @desc
 */

public class ProducerTest {
    public static void main(String[] args) throws Exception {
        // 创建一个producer，参数为Producer Group名称
        DefaultMQProducer producer = new DefaultMQProducer("MobianProducer");
        // 指定nameServer地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        // 设置当发送失败时重试发送的次数，默认为2次
        producer.setRetryTimesWhenSendFailed(3);
        // 设置发送超时时限为5s，默认3s
        producer.setSendMsgTimeout(5000);

        // 开启生产者
        producer.start();

        // 生产并发送100条消息
        while (true) {
            for (int i = 0; i < 1; i++) {
                byte[] body = ("Hi," + i).getBytes();
                Message msg = new Message("TopicTest", "Tag", body);
                // 为消息指定key
                msg.setKeys("key-" + i);
                // 同步发送消息
                SendResult sendResult = producer.send(msg);
                System.out.println(sendResult);
            }
            Thread.sleep(1000 * 5);
        }
        // 关闭producer
    }
}

