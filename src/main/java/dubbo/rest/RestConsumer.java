package dubbo.rest;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

/**
 * @author dongli
 * @create 2023/7/12 11:33
 * @desc
 */

public class RestConsumer {
    public static void main(String[] args) {
        System.setProperty("dubbo.application.logger", "slf4j");
        ReferenceConfig<RestService> ref = new ReferenceConfig<>();
        ref.setInterface(RestService.class);
        ref.setCheck(false);
        ref.setProtocol("rest");

        ref.setTimeout(100000);
        ref.setApplication(new ApplicationConfig("rest-consumer"));
        ref.setRegistry(new RegistryConfig("rest://127.0.0.1:2188"));
        final RestService tripleService = ref.get();

        System.out.println("dubbo ref started");
        String result =  tripleService.sayHello("123");
        System.out.println(Thread.currentThread().getName() + " result :" + result);
    }
}
