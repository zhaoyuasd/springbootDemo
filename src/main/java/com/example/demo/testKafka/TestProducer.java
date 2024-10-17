package com.example.demo.testKafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;

public class TestProducer {

    public static final String bootStrap = "wmst.netopstec.com:9092";

    public static final String TOPIC = "test_top";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrap);
        // 序列化协议  下面两种写法都可以
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"org.apache.kafka.clients.producer.RoundRobinPartitioner");
        // 重新拉取元数据的 间隔 默认是 10 min
        properties.put(ProducerConfig.METADATA_MAX_IDLE_CONFIG,"30000");
        //过滤器 可配置多个用逗号隔开
        //构造 KafkaProducer
        KafkaProducer<String, String> producer = new KafkaProducer(properties);

        while (true) {
            ProducerRecord<String, String> record = new ProducerRecord(TOPIC, UUID.randomUUID().toString(),"Hello World! " + UUID.randomUUID().toString());
            try {
                producer.send(record);
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
