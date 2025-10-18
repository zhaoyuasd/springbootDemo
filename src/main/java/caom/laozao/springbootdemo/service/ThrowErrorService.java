package caom.laozao.springbootdemo.service;

import caom.laozao.springbootdemo.Mapper.UserMapper;
import caom.laozao.springbootdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dongli
 * @create 2023/6/25 17:34
 * @desc
 */

@Service
public class ThrowErrorService {
    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void insertError() {
        User user = new User();
        user.setId(1L);
        user.setUserName("1234567890-asrtyuiocvbnm");
        userMapper.insert(user);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void insertError2() {
        try {

        } catch (Exception  e) {
          e.printStackTrace();
        }
        User user = new User();
        user.setId(2L);
        user.setUserName("1234567890-asrtyuiocvbnm");
        userMapper.insert(user);
        int i = 1/ 0;
    }
}
