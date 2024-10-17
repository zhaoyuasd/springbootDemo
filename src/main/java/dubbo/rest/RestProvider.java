package dubbo.rest;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.util.concurrent.CountDownLatch;

/**
 * @author dongli
 * @create 2023/7/12 13:01
 * @desc
 */

public class RestProvider {
    public static void main(String[] args) throws InterruptedException {

            System.setProperty("dubbo.application.logger", "slf4j");
            ServiceConfig<RestService> service = new ServiceConfig<>();
            service.setInterface(RestService.class);
            service.setApplication(new ApplicationConfig("rest-provider"));
            service.setRef(new RestServiceImpl());
            service.setProtocol(new ProtocolConfig("rest", 50051));
            service.export();
            System.out.println("rest dubbo service started");
            new CountDownLatch(1).await();

    }
}
