package util;

import javax.xml.bind.annotation.XmlElement;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 通过实体类生成建表语句
 */
public class GenerateSqlFromEntity2 {
    //
    public static Map<String, String> javaProperty2SqlColumnMap = new HashMap<>();

    static {
        javaProperty2SqlColumnMap.put("Integer", "INTEGER");
        javaProperty2SqlColumnMap.put("Short", "tinyint");
        javaProperty2SqlColumnMap.put("Long", "bigint");
        javaProperty2SqlColumnMap.put("BigDecimal", "decimal(19,2)");
        javaProperty2SqlColumnMap.put("Double", "double precision not null");
        javaProperty2SqlColumnMap.put("Float", "float");
        javaProperty2SqlColumnMap.put("Boolean", "bit");
        javaProperty2SqlColumnMap.put("Timestamp", "datetime");
        javaProperty2SqlColumnMap.put("String", "VARCHAR(255)");
        javaProperty2SqlColumnMap.put("Date", "datetime");
    }

    //
    public static String createTable( Class obj, String tableName ) throws IOException {
        Field[] fields = null;
        fields = obj.getDeclaredFields();
        String param = null;
        String column = null;
        Class annotationType = null;
        XmlElement xmlElement = null;

        StringBuilder stb = null;
        stb = new StringBuilder(50);

        if (tableName == null || tableName.equals("")) {
            // 未传表明默认用类名
            tableName = obj.getName();
            tableName = tableName.substring(tableName.lastIndexOf(".") + 1);
        }

        stb.append("create table ").append(tableName).append(" (\r\n");

        //System.out.println( tableName );

        boolean firstId = true;

        for ( Field f : fields ) {
            column = f.getName();

            if (column.equals("serialVersionUID")) {
                continue;
            }

            param = f.getType().getSimpleName();
            stb.append( "    " + column );     // 一般第一个是主键

            stb.append(" ");
            stb.append( javaProperty2SqlColumnMap.get(param) );

         /*if (param instanceof Integer) {
                   stb.append(" INTEGER ");
               } else if (param instanceof Short) {
                   stb.append(" tinyint ");
               } else if (param instanceof Long) {
                   stb.append(" bigint ");
               } else if (param instanceof BigDecimal) {
                   stb.append(" decimal(19,2) ");
               } else if (param instanceof Double) {
                   stb.append(" double precision not null ");
               } else if (param instanceof Float) {
                   stb.append(" float ");
               } else if (param instanceof Boolean) {
                   stb.append(" bit ");
               } else if (param instanceof Timestamp) {
                   stb.append(" datetime ");
               } else {
                   stb.append("  ");  //根据需要自行修改
               }*/

            if (firstId) {//类型转换
                stb.append(" PRIMARY KEY");
                firstId = false;
            }

            // 获取字段中包含fieldMeta的注解
            // 2、获取属性上的所有注释
            //@XmlElement(name = "注解")
            Annotation[] allAnnotations = f.getAnnotations();
            ImportExcelColumnProperty property = f.getAnnotation(ImportExcelColumnProperty.class);
            if (Objects.nonNull(property)) {
                stb.append(" COMMENT '").append(property.desc()).append("'");
            }
            /*for(Annotation an : allAnnotations){
                stb.append(" COMMIT '");
                xmlElement = (XmlElement)an;
                annotationType = an.annotationType();
                param = ((XmlElement) an).name();
                System.out.println("属性 "+f.getName()+"-----的注释类型有: " + param);
                stb.append(param).append("'");
            }*/
            stb.append(",\r\n");
        }

        String sql = null;
        sql = stb.toString();

        sql = sql  + ") ENGINE = INNODB DEFAULT  CHARSET= utf8mb4;\r\n";

        return sql;
    }

    // main
    public static void main( String[] args ) throws IOException {
        String tableName = "wms_feed_exception_order";
        String re = createTable(WmsFeedExceptionOrder.class, tableName);
        System.out.println( re );
    }

}

