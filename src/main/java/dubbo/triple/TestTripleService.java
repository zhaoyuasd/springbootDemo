package dubbo.triple;

import org.apache.dubbo.common.stream.StreamObserver;

/**
 * @author dongli
 * @create 2023/6/26 10:20
 * @desc
 */
public interface TestTripleService {
    String sayHello(String namem);

    void sayHelloServerStream(String request, StreamObserver<String> response);
}
