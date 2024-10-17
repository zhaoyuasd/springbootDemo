package caom.laozao.springbootdemo.controller;

import caom.laozao.springbootdemo.TestComponent;
import caom.laozao.springbootdemo.entity.User;
import caom.laozao.springbootdemo.service.OkService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dongli
 * @create 2023/6/25 17:40
 * @desc
 */

//@RestController
public class TestController {

   /* @Autowired
    private TestComponent testComponent;
    @Autowired
    private OkService okService;

    @RequestMapping("/test")
    public String test() {
      //  okService.insert();
        testComponent.testShow();
        return "ok";
    }*/

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("str")
    public String str(@RequestBody String str) {
        System.out.println(str);
        User user = new User();
        user.setUserName("tt");
        user.setId(123L);
        redisTemplate.opsForHash().put("map", "map", user);
        return "ok";
    }

    @PostMapping("json")
    public String json(@RequestBody JSON str) {
        System.out.println(str);
        Object ob = redisTemplate.opsForHash().get("map", "map");
        System.out.println(JSON.toJSONString(ob));
        return "ok";
    }
}
