package dubbo.rest;

import org.apache.dubbo.rpc.RpcContext;

/**
 * @author dongli
 * @create 2023/7/12 11:32
 * @desc
 */

public class RestServiceImpl implements  RestService {
    @Override
    public String sayHello(String name) {
        System.out.println(Thread.currentThread().getName() + " call :" + name);
        System.out.println(RpcContext.getContext().getAttachment("traceId1"));
        System.out.println(RpcContext.getServerContext().getAttachment("traceId2"));

        System.out.println(RpcContext.getContext().getObjectAttachment("traceId3"));
        System.out.println(RpcContext.getServerContext().getObjectAttachment("traceId4"));
        int i = 3 /0;
        return "hello :" + name;
    }
}
