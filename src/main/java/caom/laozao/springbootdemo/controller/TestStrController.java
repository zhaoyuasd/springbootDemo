package caom.laozao.springbootdemo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dongli
 * @create 2024/8/19 13:21
 * @desc
 */

@RestController
public class TestStrController {
   @PostMapping("hello")
    public String hello() {
        return "hello";
    }
}
