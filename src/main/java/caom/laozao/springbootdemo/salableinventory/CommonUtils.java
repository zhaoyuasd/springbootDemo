package caom.laozao.springbootdemo.salableinventory;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by sanze on 2016/12/22.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class CommonUtils {

    /**
     * 获取ID
     *
     * @return long uuid
     */
    public static Long getLongUUID() {
//        return byteToLong(UUID.randomUUID().toString().getBytes());
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }


    public static String getDateFormatString(Date date) {
        return getDateFormatString(date, "yyyy-MM-dd");
    }

    public static String getDateFormatString(Date date, String format) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字节数组转Long.
     *
     * @param b 字节数组
     * @return 长整形
     */
    public static long byteToLong(byte[] b) {
        long s;
        long s0 = b[0] & 0xff;// 最低位
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;
        long s4 = b[4] & 0xff;// 最低位
        long s5 = b[5] & 0xff;
        long s6 = b[6] & 0xff;
        long s7 = b[7] & 0xff;

        // s0不变
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }

    /**
     * 生成签名
     *
     * @param obj    参数对象
     * @param secret 密钥，调用登录接口返回的session，第三方则是AppSecret
     * @return 签名字符串 string
     */



    /**
     * 生成签名（云集）
     *
     * @param obj
     * @param secret
     * @return
     */
    public static String generateSignNoEq(Object obj, String secret) {
        String signString = initSignStringNoEq(obj, secret);
        //MD5加密
        return MD5Utils.getMd5(signString);
    }

    public static String initSignStringNoEq(Object obj, String secret) {
        Map<String, String> map = new HashMap<>();
        try {
            map = BeanUtils.describe(obj);
        } catch (Exception e) {
            log.error("error in CommonUtils --> generateSign: ", e);
        }
        //删除生成的class键值对
        map.remove("class");
        map.remove("clazz");
        //删除sign键值对
        map.remove("sign");
        //删除jdDecryptLog键值对
        map.remove("jdDecryptLog");
        //删除生成的方法
        map.remove("responseClass");

        List<String> keyList = new ArrayList<>(map.keySet());
        //按照首字母顺序排序
        Collections.sort(keyList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        StringBuilder sb = new StringBuilder();
        sb.append(secret);
        for (String key : keyList) {
            sb.append(key).append(map.get(key));
        }
        sb.append(secret);
        return sb.toString();
    }

    /**
     * 生成签名（麦苗）
     *
     * @param obj
     * @param secret
     * @return
     */
    public static String generateSignByMm(Object obj, String secret) {
        String signString = initSignStringByMm(obj, secret);
        //MD5加密
        return MD5Utils.getMd5(signString);
    }

    public static String initSignStringByMm(Object obj, String secret) {
        Map<String, String> map = new HashMap<>();
        try {
            map = BeanUtils.describe(obj);
        } catch (Exception e) {
            log.error("error in CommonUtils --> initSignStringByMm: ", e);
        }
        //删除生成的class键值对
        map.remove("class");
        //删除sign键值对
        map.remove("sign");
        //删除sign键值对
        map.remove("pageSize");
        //删除sign键值对
        map.remove("pageNumber");

        List<String> keyList = new ArrayList<>(map.keySet());
        //按照首字母顺序排序
        Collections.sort(keyList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        StringBuilder sb = new StringBuilder();
        for (String key : keyList) {
            if (StringUtils.isEmpty(map.get(key))) {
                continue;
            }
            sb.append(key).append("=").append(map.get(key)).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("{").append(secret).append("}");
        return sb.toString();
    }

    /**
     * 压缩字符串，最后用base64加密
     *
     * @param primStr 原始字符串
     * @return 返回压缩加密后的字符串 string
     */
    public static String compress(String primStr) throws Exception {
        if (primStr == null || primStr.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            @Cleanup GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            log.error("error in CommonUtils --> compress: ", e);
            throw e;
        }
        return Base64.encodeBase64String(out.toByteArray());
    }

    /**
     * 解压字符串，解压前用base64解密
     *
     * @param compressedStr the compressed str
     * @return 返回解密解压后的字符串 string
     */
    public static String decompress(String compressedStr) throws Exception {
        if (compressedStr == null) {
            return null;
        }

        byte[] compressed;
        String decompressed;
        try {
            @Cleanup ByteArrayOutputStream out = new ByteArrayOutputStream();

            compressed = Base64.decodeBase64(compressedStr);
            @Cleanup ByteArrayInputStream in = new ByteArrayInputStream(compressed);
            @Cleanup GZIPInputStream zipIS = new GZIPInputStream(in);

            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = zipIS.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString("UTF-8");
        } catch (IOException e) {
            log.error("error in CommonUtils --> decompress: ", e);
            throw e;
        }

        return decompressed;
    }

    /**
     * 判断是否是手机号码，必须是13、15、18开头，并且11位长度
     */
    public static Boolean IsPhoneNumber(String str) {
        return Pattern.matches("^[1]+[3,5,8]+\\d{9}$", str);
    }

    /**
     * 是否邮箱地址
     */
    public static Boolean IsMatchEmail(String str) {
        return Pattern.matches("^([\\w-]+\\.+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", str);
    }

    /**
     * 是否为数字
     */
    public static Boolean IsNumeric(String str) {
        return Pattern.matches("^[0-9]+$", str);
    }

    /**
     * 是否为字母
     */
    public static Boolean IsLetter(String str) {
        return Pattern.matches("^[a-zA-Z]+$", str);
    }

    /**
     * 是否为汉字
     */
    public static Boolean IsChinese(String str) {
        return Pattern.matches("^[\\u4e00-\\u9fa5]+$", str);
    }

    /**
     * 生成随机的6位验证码
     */
    public static String GetRandomSmsCode() {
        StringBuilder smsCode = new StringBuilder("000000");
        Date curDate = new Date();
        Long milliseconds = curDate.getTime();
        Random rd = new Random(milliseconds);
        for (int i = 0; i < 6; i++) {
            String str = String.valueOf(rd.nextInt(9));
            char c = str.charAt(0);
            smsCode.setCharAt(i, c);
        }
        return smsCode.toString();
    }


    /**
     * 将Integer转化为boolean
     *
     * @param i 1 or 0
     * @return 1:true, 0:false, null:false
     */
    public static boolean toBoolean(Integer i) {
        return i != null && BooleanUtils.toBoolean(i);
    }

    /**
     * 将Integer转化为boolean
     *
     * @param i 1 or 0
     * @return 1:true, 0:false, null:false
     */
    public static Integer toInteger(Boolean i) {
        if (i == null) {
            return 0;
        }
        return i ? 1 : 0;
    }

    /**
     * 记录异常日志
     *
     * @param e
     * @param filePath
     */


    /**
     * 记录文本日志
     *
     * @param txt
     * @param filePath
     */


    /**
     * 记录文本日志，指定文件名
     *
     * @param txt
     * @param filePath
     * @param fileName
     */


    public static void saveFile(String content, String toFileName) {
        BufferedWriter bw = null;
        try {
            //false--不累加写入，true---累加写入
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(toFileName, true), "UTF-8"));
            bw.write(content + "\r\n");
        } catch (Exception e) {
            log.error("error in CommonUtils ===> saveFile:", e);
        } finally {
            if (bw != null) {
                try {
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    log.error("error in CommonUtils ===> saveFile:", e);
                }
            }
        }
    }

    public static boolean newFolder(String folderPath) {
        try {
            File myFilePath = new File(folderPath);
            if (!myFilePath.exists()) {
                return myFilePath.mkdirs();
            }
            return true;
        } catch (Exception e) {
            log.error("error in CommonUtils ===> newFolder:", e);
            return false;
        }
    }

    /**
     * 后台任务执行时间表达式转化成时间(定时，非循环)
     *
     * @param cronExpression
     * @return
     */
    public static Date getCronExpressionToTime(String cronExpression) throws Exception {
        String[] cron = cronExpression.split(" ");
        if (cron.length < 7 || !cron[5].equals("?") || cronExpression.indexOf("*") > -1 || cronExpression.indexOf("/") > -1 || cronExpression.indexOf(",") > -1) {
            return null;
        }
        String timeStr = cron[6] + "-" +
                (cron[4].length() == 1 ? ("0" + cron[4]) : cron[4]) + "-" +
                (cron[3].length() == 1 ? ("0" + cron[3]) : cron[3]) + " " +
                (cron[2].length() == 1 ? ("0" + cron[2]) : cron[2]) + ":" +
                (cron[1].length() == 1 ? ("0" + cron[1]) : cron[1]) + ":" +
                (cron[0].length() == 1 ? ("0" + cron[0]) : cron[0]) + ":";
        Date resultTime = DateUtils.parseDate(timeStr, "yyyy-MM-dd HH:mm:ss");
        return resultTime;
    }

    /**
     * 生成后台任务执行时间表达式
     *
     * @param dateStr
     * @return
     */
    public static String initCronExpression(String dateStr, Integer type) {
        String resultStr = "s m h * * ?";
        String[] dateStrArray = dateStr.split(":");
        if (type == 0) {
            //定时每天跑一次任务
            String hour = Integer.valueOf(dateStrArray[0]).toString();
            String minute = Integer.valueOf(dateStrArray[1]).toString();
            String second = "00";
            resultStr = resultStr.replace("h", hour).replace("m", minute).replace("s", second);
        } else {//定时一天内每隔多长时间跑一次任务
            int seconds = Integer.valueOf(dateStr);
            if (seconds < 60) {
                resultStr = resultStr.replace("h", "*").replace("m", "*").replace("s", "0/" + seconds);
            } else if (seconds >= 60 && seconds < 3600) {
                resultStr = resultStr.replace("h", "*").replace("m", "0/" + (seconds / 60)).replace("s", "0");
            } else {
                resultStr = resultStr.replace("h", "0/" + (seconds / 3600)).replace("m", "0").replace("s", "0");
            }
        }
        return resultStr;
    }

    /**
     * 获取第一个指定字符中间的文本
     * 比如传入，#fdsaflsdf#545432543，#。 会返回fdsaflsdf
     *
     * @param source
     * @param separator
     * @return
     */



//    public static void main(String[] args) throws Exception {
//        String str = "H4sIAAAAAAAAAIuuVspMUbIyN7Ew1FEqSCxKzSvxBPNNLXWUilLTM/Pz/BJzU5WslJ4uWflsy1IlmGhIZQFQFKipKrPAOT8FyM4rzckBmpGRn5fqWJSaiCKYmReZmQfjJeZkJhZDOLWxAH4M1AOBAAAA";
//        System.out.println(CommonUtils.decompress(str));
//    }
}