package dubbo.rest;

/**
 * @author dongli
 * @create 2023/7/12 11:32
 * @desc
 */

public class RestServiceImpl implements  RestService {
    @Override
    public String sayHello(String name) {
        System.out.println(Thread.currentThread().getName() + " call :" + name);
        return "hello :" + name;
    }
}
