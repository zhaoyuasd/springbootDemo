package caom.laozao.springbootdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author dongli
 * @create 2023/6/6 13:56
 * @desc
 */

@Component
public class TestComponent {
    @Value("${test.value}")
    private String value;

    public void testShow(){
        System.out.println(value);
    }
}
