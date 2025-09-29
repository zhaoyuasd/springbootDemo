package dubbo.rest;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.util.concurrent.CountDownLatch;

/**
 * @author dongli
 * @create 2023/7/12 13:01
 * @desc
 */

public class RestProvider {
    public static void main(String[] args) throws InterruptedException {

            System.setProperty("log4j.rootLogger", "debug");

            ProviderConfig providerConfig = new ProviderConfig();
            System.setProperty("dubbo.qos.port", "22223");

            System.setProperty("dubbo.application.logger", "slf4j");
            ServiceConfig<RestService> service = new ServiceConfig<>();
            service.setInterface(RestService.class);
            service.setApplication(new ApplicationConfig("rest-provider"));
            service.setRef(new RestServiceImpl());
            service.setProtocol(new ProtocolConfig("dubbo", 50051));
            service.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2188"));
            System.out.println("rest dubbo service started  111" );
            service.export();
            System.out.println("rest dubbo service started 222");
            new CountDownLatch(1).await();

    }
}
