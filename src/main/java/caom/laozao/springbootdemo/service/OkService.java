package caom.laozao.springbootdemo.service;

import caom.laozao.springbootdemo.Mapper.UserMapper;
import caom.laozao.springbootdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author dongli
 * @create 2023/6/25 17:36
 * @desc
 */

@Service
public class OkService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ThrowErrorService errorService;

    @Transactional(rollbackFor = Exception.class)
    public void insert() {
        User user = new User();
        user.setId(2L);
        user.setUserName("234");
        userMapper.insert(user);
        try {
            errorService.insertError();
            /*User user2 = new User();
            user2.setId(1L);
            user2.setUserName("1234567890-asrtyuiocvbnm");
            userMapper.insert(user2)*/;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}
