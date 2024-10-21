package caom.laozao.springbootdemo.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author dongli
 * @create 2024/10/21 15:13
 * @desc
 */

@Slf4j
public class CglibDynamicProxy implements MethodInterceptor {

    private Object target;

    public CglibDynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (objects != null && objects.length > 0) {

            for (int i = 0; i < objects.length; i++) {
                if (objects[i] instanceof Runnable) {
                    objects[i] = addLocalInfoToRunnable((Runnable) objects[i]);
                }
            }
        }
        Method originalMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
        Object invoke = originalMethod.invoke(target, objects);
        return invoke;
    }

    private Runnable addLocalInfoToRunnable(Runnable command) {
        return () -> {
            Class<?> mdc = null;
            String traceId = "traceId";
            Method putTraceId = null;
            {
                try {
                    mdc = Class.forName("org.slf4j.MDC");
                    putTraceId = mdc.getMethod("put", String.class, String.class);
                    putTraceId.invoke(null, traceId, UUID.randomUUID().toString());
                    command.run();
                    System.out.println("proxy success");
                } catch (Exception e) {
                    log.error("ThreadPoolExecutor error put traceId ", e);
                } finally {
                    try {
                        if (putTraceId != null) {
                            Method remove = mdc.getMethod("remove", String.class);
                            remove.invoke(null, traceId);
                        }
                    } catch (Exception e) {
                        log.error("ThreadPoolExecutor error putTraceId traceId ", e);
                    }
                }
            }

        };

    }
}
