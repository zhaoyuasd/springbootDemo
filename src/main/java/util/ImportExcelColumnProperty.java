package util;

import java.lang.annotation.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

/**
 * 导入excel 注解类
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ImportExcelColumnProperty {

    /**
     * 行标题 必填
     */
    String desc();

    /***
     * 类型转换器
     */
    Class<? extends Function> conversion() default DefaultConversion.class;

    /**
     *  true 代表可以为空 false代表不可以为空
     * @return
     */
    boolean ignoreNull() default true;


    /**
     *    默认转换器 缺省值 不做调用
     */
    class DefaultConversion implements Function {
        @Override
        public Object apply(Object o) {
            return o;
        }
    }

    /**
     * 数字类型 转为 BigDecimal
     */
    class NumberToBigDecimalConversion implements Function {
        @Override
        public Object apply(Object o) {
            if (Objects.isNull(o)) {
                return null;
            }
            try {
                if (o instanceof String) {
                    return new BigDecimal(o.toString());
                }
                if (o instanceof Number) {
                    Number transTime = (Number) o;
                    return new BigDecimal(transTime.toString());
                }
                throw new RuntimeException(o + " exception : 不是数字类型无法处理");
            } catch (Exception e) {
                throw new RuntimeException(o + " exception : 不是数字类型无法处理");
            }
        }
    }

    class OtherToStringConversion implements Function {
        @Override
        public Object apply(Object o) {
            if (Objects.isNull(o)) {
                return null;
            } else {
                return o.toString();
            }
        }
    }

    class OtherToIntegerConversion implements Function {
        @Override
        public Object apply(Object o) {
            if (Objects.isNull(o)) {
                return null;
            } else {
                return Integer.valueOf(o.toString());
            }
        }
    }

    class OtherToLongConversion implements Function {
        @Override
        public Object apply(Object o) {
            if (Objects.isNull(o)) {
                return null;
            } else {
                return Long.valueOf(o.toString());
            }
        }
    }

    /**
     * @author dongli
     */
    class StringToDateConversion implements Function{
        @Override
        public Object apply(Object o) {
            if (Objects.isNull(o)) {
                return null;
            }
            if (o instanceof Date) {
                return o;
            }
            String datePattern = getDatePatter(o.toString());

            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

            try {
                return sdf.parse(o.toString());
            } catch (ParseException e) {
                throw new RuntimeException("日期请按照[yyyy-MM-dd HH:mm:ss]或者[yyyy/MM/dd HH:mm:ss]格式填写");
            }
        }

        private String getDatePatter(String dateStr) {
            String[] dateAndTime = dateStr.split(" ");
            String date = dateAndTime[0];
            int index = date.indexOf("-");
            String pattern = null;
            if (index > 0) {
                pattern = "yyyy-MM-dd";
            } else {
                pattern = "yyyy/MM/dd";
            }

            if (dateAndTime.length == 2) {
                String time = dateAndTime[1];
                String[] timeStr = time.split(":");
                if (timeStr.length == 1) {
                    pattern += " HH";
                }
                if (timeStr.length == 2) {
                    pattern += " HH:mm";
                }
                if (timeStr.length == 3) {
                    pattern += " HH:mm:ss";
                }
            }
            return pattern;
        }
    }

}
