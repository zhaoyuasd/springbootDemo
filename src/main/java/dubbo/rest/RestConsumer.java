package dubbo.rest;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @author dongli
 * @create 2023/7/12 11:33
 * @desc
 */

public class RestConsumer {
    public static void main(String[] args) {
        System.setProperty("dubbo.application.logger", "slf4j");
        System.setProperty("dubbo.qos.port", "22223");
        ReferenceConfig<RestService> ref = new ReferenceConfig<>();
        ref.setInterface(RestService.class);
        ref.setCheck(false);
        ref.setProtocol("dubbo");
        ref.setProviderPort(20881);


        ref.setTimeout(100000);
        ref.setApplication(new ApplicationConfig("rest-consumer"));
        ref.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2188"));
        final RestService tripleService = ref.get();

        System.out.println("dubbo ref started");
        RpcContext.getContext().setAttachment("traceId1", "dubbo123456789123456780");
        RpcContext.getServerContext().setAttachment("traceId2", "getServerContext");
        RpcContext.getContext().setObjectAttachment("traceId3", "dubbo1234567890");
        RpcContext.getServerContext().setObjectAttachment("traceId4", "getServerContext");
        String result =  tripleService.sayHello("123");
        System.out.println(Thread.currentThread().getName() + " result :" + result);
    }
}
