/**
 * @author dongli
 * @create 2024/8/23 11:10
 * @desc
 */

public class SqlScriptGenerate {

    private final static String RAW_SQL_TABLE_BRAND_ID = "-- 更新表数据[#]将stockId替换成聚石塔下对接长仓库的stockId\n" +
            "update erp_oms_$.# t set stockId = (\n" +
            "    select id from erp_oms_$.sync_stock_top where outerCode = (select outerCode from erp_oms_$.sync_stock where id = t.stockId)\n" +
            ") where\n" +
            "   t.stockId is not null and \n" +
            "  exists (\n" +
            "   select id from erp_oms_$.sync_stock_top where outerCode = (select outerCode from erp_oms_$.sync_stock where id = t.stockId)\n" +
            ");";

    private  final static String RAW_SQL_BRAND_ID = "-- 只保留 京东数据库独有的\n" +
            "delete from erp_oms_$.sync_stock  where outerCode in  (select distinct  outerCode from  erp_oms_$.sync_stock_top );\n" +
            "\n" +
            "-- 删除表 sync_stock_freeze \n" +
            "drop table erp_oms_$.sync_stock_freeze;\n" +
            "drop table erp_oms_$.oms_task;";
    private final static String BRAND_IDS = "aekyung,dachongai,friso,feliway,dutchlady";

    private final static  String  TABLE_NAME = "sales_do,channel_auto_allot_express,channel_stock,po_order,sales_amount_share_sales_do,sales_order," +
            "sales_order_prom, sales_order_register,sales_rtn_order,split_sales_do_header,sync_stock_freeze_change_log";
    public static void main(String[] args) {
        for (String brandId : BRAND_IDS.split(",")) {
            System.out.println("/***************" + brandId + "****** start*********/");
            for (String tableName : TABLE_NAME.split(",")) {
                String sql = RAW_SQL_TABLE_BRAND_ID.replace("$", brandId).replace("#", tableName);
                System.out.println(sql);
            }
            String sql = RAW_SQL_BRAND_ID.replace("$", brandId);
            System.out.println(sql);
            System.out.println("/***************" + brandId + "****** end*********/");
            System.out.println("\r\n");
        }
    }
}
