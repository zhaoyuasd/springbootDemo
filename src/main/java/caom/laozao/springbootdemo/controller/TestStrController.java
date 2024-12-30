package caom.laozao.springbootdemo.controller;

import caom.laozao.springbootdemo.component.CglibDynamicProxy;
import cn.hutool.core.collection.CollectionUtil;
import com.liubs.findinstances.jvmti.InstancesOfClass;
import org.apache.tomcat.util.net.NioEndpoint;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author dongli
 * @create 2024/8/19 13:21
 * @desc
 */

@RestController
public class TestStrController {
    private String info = "info";


   @RequestMapping("hello")
    public String hello() {
        return "hello :" + info;
    }

    @RequestMapping("change")
    public String change() throws Exception {
        List<NioEndpoint> list =  InstancesOfClass.getInstanceList(NioEndpoint.class);
        if (CollectionUtil.isEmpty(list)) {
            return "no load";
        }
        NioEndpoint point = list.get(0);

        List<ThreadPoolExecutor> executors =  InstancesOfClass.getInstanceList(ThreadPoolExecutor.class);
        if (CollectionUtil.isEmpty(executors)) {
            return "no load";
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ThreadPoolExecutor.class);
        enhancer.setCallback(new CglibDynamicProxy(executors.get(0)));
        Constructor<?>[] constructors = ThreadPoolExecutor.class.getConstructors();

        Constructor<?> constructor = constructors[0];
        Object[] arguments = {1, 1, 100L, TimeUnit.SECONDS, new ArrayBlockingQueue(1)};
        ThreadPoolExecutor executor = (ThreadPoolExecutor) enhancer.create(constructor.getParameterTypes(), arguments);
        Field field = getFieldByName(NioEndpoint.class, "executor");
        field.setAccessible(true);
        field.set(point, executor);

        return "changed success";
    }

    private Field getFieldByName(Class<?> clz, String name) {
       while (!clz.isAssignableFrom(Object.class)) {
           try {
               return clz .getDeclaredField(name);
           } catch (NoSuchFieldException e) {
               clz = clz.getSuperclass();
           }
       }
       return null;
    }
}
