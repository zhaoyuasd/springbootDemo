package rabbitmq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author: dongli
 * @since: 2026/4/10 13:51
 * @description:
 */

public class RabbitMqProvider {
    private static final String QUEUE_NAME = "prod_wms_registration_queue";

    public static void main(String[] args) throws Exception {
        // 1. 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("118.31.169.153");
        factory.setPort(5672);
        factory.setUsername("netopserp");
        factory.setPassword("zjhzwy0556");
        factory.setVirtualHost("/");

        // 2. 建立连接和信道
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        // 注册确认监听器
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("发送成功确认，deliveryTag=" + deliveryTag + ", multiple=" + multiple);

            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("发送失败确认，deliveryTag=" + deliveryTag + ", multiple=" + multiple);
            }
        });

        // 开启发布确认模式
       String expNos =
               ",YT8851868741711,YT8851429725413,YT8851669825861,YT8852690520176,YT8851427623056,YT8851713226712,YT8851523469589,YT8851523469589,YT8851423501770,YT8851434212881,YT8851426986475,YT8852951489268,YT8852360213179,YT8851039688135,YT8846659036743,YT8855368291677,YT8854377144060,YT8854688304575,YT8853817918681,YT8843498113556,YT8854105155593,YT8849733614873,YT8854340603718,YT8853220504552,YT8853220504552,YT8853289179803,YT8853523999598,YT8854251497437,YT8853579511924,YT8837985929917,YT8853835209802,9815161663462,YT8850003425174,SF5108942118109,YT8853221555524,YT8853230028871,YT8853574589513,YT8854195131947,YT8852357486924,YT8852972017990,YT8851408940208,YT8850564781298,YT8850564781298,YT8848421904568,YT8850465185627,YT8851434114006,YT8850924482399,YT8851431985809,YT8851599509109,YT8851076027667,YT8850726680904,YT8851070706566,YT8851427488314,YT8851488792764,YT8851368506353,YT8851368506353,YT8851598177594,YT8851380378101,YT8850638658268,YT8851047083968,YT8852989084989,YT8851230575074,YT8851072992354,YT8851431964761,YT8851431964761,YT8851353304797,YT8851367044788,YT8850560931141,YT8851356763311,YT8850404552974,YT8850404552974,YT8851861536776,YT8851355952109,YT8851643329378,YT8851860691119,YT8851037276053,YT8856022706647,YT8850611750840,YT8856606921442,YT8853642289701,YT8837979330334,YT8852625670020,YT8852055407355,YT8853000820906,YT8853000820906,YT8851702856656,YT8843382071331,YT8852548700420,YT8856911119435,YT8856657822046,9815313667264,YT8856907088790,YT8856522807299,YT8853522736204,YT8855965569036,YT8856853023960,YT8852361069252,9815313532770,777394102768468,777396206180207,777394658438336,777396155670193,777395596903346,777396565674250,YT8853819635386,777396336024438,777397816440455,777396903314518,777396615067902,777397257298935,777396256126665,777397755307219,777396467964675,777397699769516,777398337672756,777395873188300,777393674271689";
       for(String expNo : expNos.split(",")) {
           RegistrationExpEntity expEntity = new RegistrationExpEntity(expNo, "zhuangniu");
           channel.basicPublish("", QUEUE_NAME, null, JSON.toJSONString(expEntity).getBytes("UTF-8"));
       }



        // 3. 声明队列
        // queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
      //  channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 4. 发送消息




        // 5. 消费消息

        // 6. 关闭资源
        channel.close();
        connection.close();
    }




    public static class RegistrationExpEntity{
        /**
         * 运单号
         */
        private String expCode;

        //品牌ID
        private String brandId;


        public RegistrationExpEntity( String expCode, String brandId ) {
            this.brandId= brandId;
            this.expCode =expCode;
        }

        public String getBrandId() {
            return brandId;
        }

        public String getExpCode() {
            return expCode;
        }
    }
}
