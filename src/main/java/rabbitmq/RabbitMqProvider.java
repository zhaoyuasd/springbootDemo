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
               "YT8845594486088,YT8856574633785,YT8856574633785,777398766533509,YT8856334393139,YT8856783266345,YT8857531292496,9815350662629,9815366049298,YT8855765894257,YT8856344641671,YT8855975142418,YT8857552583207,YT8855035237535,YT8855951593755,YT8855037423435,YT8856924666863,YT8855704233484,9815345973319,YT8855973657675,9815345973319,YT8855771328883,YT8856945258955,YT8856612416556,YT8856573125236,YT8856573125236,YT8856784060364,YT8852854345351,YT8856609803402,YT8856452578702,YT8855036670784,YT8855036670784,YT8856926136067,YT8855032890209,YT8855196727892,YT8855752439797,YT8855732809714,YT8856297055947,YT8856938846893,YT8855951537742,YT8853693508259,YT8855056273347,YT8855173278894,YT8856219096478,YT8856074876341,YT8855934680054,YT8855413655074,YT8855933600187,YT8856934684556,YT8855337210991,YT8856921776510,YT8856493290102,YT8855769393233,YT8855765946224,YT8855947518405,YT8856913477326,YT8855037512762,YT8855756151428,777397864003769,777398484533377,777398532689581,777397961279111,777397910956945,777398363897627,777398347066699,777398354362470,777398337859525,777397910569050,777397814174009,777397810068982,777398395270764,YT8855411743203,YT8856451005812,YT8855979204422,YT8855883595858,YT8855382487847,YT8855738604241,YT8856342275427,YT8855181453472,YT8855389638217,YT8855177282121,YT8855759859547,YT8856948537367,YT8855377882363,YT8856983772266,YT8855699136162,YT8853834182429,YT8855035687484,YT8853455191217,YT8856911007301,YT8855365051478,YT8855365051478,YT8855756345553,YT8855883137434,YT8855883137434,YT8855758593174,YT8856074653287,YT8855758593174,YT8852986967917,YT8855027966445,YT8852989327037,YT8855783745913,YT8856554208345,YT8855760748816,YT8852987031277,YT8852987031277";
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
