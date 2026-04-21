package caom.laozao.springbootdemo.salableinventory;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * Created by sanze on 2016/12/22.
 */

@Slf4j
public class MD5Utils {
    private final static char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    /**
     * MD5 加密字符串
     *
     * @param primStr 初始字符串
     * @return 加密后的字符串
     */
    public static String getMd5(String primStr) {
        if(primStr == null){
            return null;
        }
        try {
            byte[] btInput = primStr.getBytes("utf-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            log.error("error in MD5Utils --> getMd5: ", e);
        }
        return null;
    }

//    public static void main(String[] args) {
//        String s = "123asdfasdf";
//        System.out.println(getMd5(s));
//        System.out.println(DigestUtils.md5Hex(s));
//    }
}
