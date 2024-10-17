package dubbo.triple;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.util.concurrent.CountDownLatch;

/**
 * @author dongli
 * @create 2023/6/26 10:21
 * @desc
 */

public class TripleProvider {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("dubbo.application.logger", "slf4j");
        ServiceConfig<TestTripleService> service = new ServiceConfig<>();
        service.setInterface(TestTripleService.class);
        service.setRef(new TestTripleServiceImpl());
        // 这里需要显示声明使用的协议为triple
        //service.setProtocol(new ProtocolConfig(CommonConstants.DUBBO_PROTOCOL, 50051));
        service.setProtocol(new ProtocolConfig(CommonConstants.TRIPLE, 50051));
        service.setApplication(new ApplicationConfig("triple-provider"));
        service.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2188"));
        service.export();
        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
