package com.example.demo.testKafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class TestConsumer {
    public static final String bootStrap = "192.168.1.193:9092";

    public static final String TOPIC = "testPartitionT";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootStrap);
        props.put("group.id", TOPIC);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        for (int i = 0;  i < 6; i++) {
            new Thread(() -> consumer(props)).start();
        }
    }

    private static void consumer(Properties props) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Collections.singletonList(TOPIC));
        try {
            System.out.println(Thread.currentThread().getName()  + " start ");
            // 轮询
            while (true) {
                // 超时100
                ConsumerRecords<String, String> records = consumer.poll(100);
                // 遍历取到的消息
                for (ConsumerRecord<String, String> record : records){
                    System.out.printf(Thread.currentThread().getName() + "  topic = %s, partition = %s, offset = %d, key = %s, value = %s\n", record.topic(), record.partition(), record.offset(), record.key(), record.value());
                }
            }
        } finally {
            // 关闭
            consumer.close();
            System.out.println(Thread.currentThread().getName()  + " close ");
        }
    }
}
