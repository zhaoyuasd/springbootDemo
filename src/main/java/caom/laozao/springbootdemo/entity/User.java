package caom.laozao.springbootdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;
import lombok.Setter;


/**
 * @author dongli
 * @create 2023/6/25 17:29
 * @desc
 */

@Getter
@Setter
@TableName("user")
public class User {
    private Long id;
    private String userName;

    @Version
    private Integer version;
}
