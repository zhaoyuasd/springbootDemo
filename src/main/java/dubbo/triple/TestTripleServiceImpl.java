package dubbo.triple;

import org.apache.dubbo.common.stream.StreamObserver;

/**
 * @author dongli
 * @create 2023/6/26 10:31
 * @desc
 */

public class TestTripleServiceImpl implements TestTripleService{

    @Override
    public String sayHello(String name) {
        System.out.println(Thread.currentThread().getName() + " call :" + name);
        int i = 1 / 0;
        return "hello :" + name;
    }

    @Override
    public void sayHelloServerStream(String request, StreamObserver<String> response) {
        for (int i = 0; i < 10; i++) {
            response.onNext("hello," + request);
        }
        response.onCompleted();
    }
}
