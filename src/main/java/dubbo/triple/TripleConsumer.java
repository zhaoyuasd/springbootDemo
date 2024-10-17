package dubbo.triple;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

import java.io.IOException;

/**
 * @author dongli
 * @create 2023/6/26 10:21
 * @desc
 */

public class TripleConsumer {
    public static void main(String[] args) throws IOException {
        System.setProperty("dubbo.application.logger", "slf4j");
        ReferenceConfig<TestTripleService> ref = new ReferenceConfig<>();
        ref.setInterface(TestTripleService.class);
        //ref.setCheck(false);
       //  ref.setProtocol(CommonConstants.DUBBO_PROTOCOL);
        ref.setProtocol(CommonConstants.TRIPLE);
       // ref.setLazy(true);
        ref.setTimeout(100000);
        ref.setApplication(new ApplicationConfig("triple-consumer"));
        ref.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2188"));
        final TestTripleService tripleService = ref.get();

       /* System.out.println("dubbo ref started");
        String result =  tripleService.sayHello("123");
        System.out.println(Thread.currentThread().getName() + " result :" + result);*/

        tripleService.sayHelloServerStream("server stream", new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println(data);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        });


    }
}
