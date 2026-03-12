package dubbo.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dongli
 * @create 2026/3/11 16:02
 * @desc
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RpcRequest {

    private String name;

    private String age;
}
