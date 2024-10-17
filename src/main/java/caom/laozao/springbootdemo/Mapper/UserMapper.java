package caom.laozao.springbootdemo.Mapper;

import caom.laozao.springbootdemo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dongli
 * @create 2023/6/25 17:32
 * @desc
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
