package caom.laozao.springbootdemo.salableinventory;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Salable inventory Excel reader.
 */
public class SalableInventoryExcelReader {

    private static final String DEFAULT_FILE_PATH =
            "D:/dependency/E管家测试环境_Ver3.7.5_20260205/"
                    + "20260419_friso_with_snapshot_SalableInventory.xlsx";

    public static void main(String[] args) throws Exception {

        String frisoUrl="https://rmsedi.rfc-friso.com/pushDataApi/stock";
        String filePath = args != null && args.length > 0 ? args[0] : DEFAULT_FILE_PATH;
        List<SalableInventoryExcel> excels = read(filePath);
        System.out.println("读取条数: " + data.size());
       // data.forEach(row -> System.out.println(JSON.toJSONString(row)));
        List<StockReport> stockReportList = convert2Stock(excels);
        Map<String, Object> params = new HashMap<>();
        params.put("kaName", FrisoPushDataConstants.KA_NAME);
        params.put("token", FrisoPushDataConstants.STOCK_TOKEN);
        params.put("data", stockReportList);
        FrisoHttpUtil.FrisoResponse frisoResponse = FrisoHttpUtil.execute(frisoUrl, params);

    }

    public static List<SalableInventoryExcel> read(String filePath) {
        return EasyExcel.read(filePath)
                .head(SalableInventoryExcel.class)
                .sheet()
                .doReadSync();
    }

    private static List<StockReport> convert2Stock(List<SalableInventoryExcel> excels) {
        List<StockReport> stockReportList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(excels)) {
            for (SalableInventoryExcel excel : excels) {
                StockReport stockReport = new StockReport();
                stockReport.setBrand(StringUtils.isNotEmpty(excel.getBrands()) ? excel.getBrands() : "未知");
                stockReport.setStockDate(excel.getDate());
                stockReport.setStoreCode(excel.getStoreCode());
                stockReport.setStoreName(excel.getStoreName());
                stockReport.setProductCode(excel.getSkuNo());
                stockReport.setProductName(excel.getProductName());
                stockReport.setBasicUnit(excel.getSkuName());
                stockReport.setCompany(excel.getUnit());
                stockReport.setType(excel.getType());
                stockReport.setAmount(Integer.valueOf(excel.getQty()));
                stockReport.setUnDelivery(Integer.valueOf(excel.getUnDelivery()));
                stockReport.setDcQude(Integer.valueOf(excel.getDcQude()));
                if (StringUtils.isNotEmpty(excel.getBoxLimit())) {
                    stockReport.setBasicBox(Integer.valueOf(excel.getBoxLimit()));
                }
                if (Objects.equals(stockReport.getType(), "正品")) {
                    stockReport.setType("正常");
                }
                stockReportList.add(stockReport);
            }
        }
        return stockReportList;
    }

}
