package excelTest;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author dongli
 * @create 2026/1/26 17:22
 * @desc
 */

public class TestExport {
    public static void main(String[] args) {
      File file = new File("test.xlsx");
      try (OutputStream os = new FileOutputStream(file)){
          exportExcel(os);
      } catch (Exception e) {
          e.printStackTrace();
      }
        System.out.println("导出结束");
    }

    public static void exportExcel(OutputStream os) throws Exception {

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("履约数据");
        // ================= 表头 =================
        Row head1 = sheet.createRow(0);
        Row head2 = sheet.createRow(1);



        List<String> towRowHeader = Lists.newArrayList("渠道", "运力商", "订单量", "履约及时率");
        List<String> firstRowHeader = Lists.newArrayList("0-3公里", "3-5公里", "5公里以上", "其他");
        List<String> secondRowHeader = Lists.newArrayList("订单量", "占比", "接单时长（分钟）", "配送时长（分钟）", "履约及时率", "配送费");

        // 先写两行的标题
        int colIdx = 0;
        for (String header : towRowHeader) {
            Cell cell1 = head1.createCell(colIdx++);
            cell1.setCellValue(header);
        }
        // 纵向合并（前 4 列）
        for (int i = 0; i < towRowHeader.size(); i++) {
            sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));
        }

        // 写带有副标题的数据
        for (String header : firstRowHeader) {
            int start = colIdx;
            Cell cell1 = head1.createCell(start);
            cell1.setCellValue(header);
            // 写子标题
            for (String subHeader : secondRowHeader) {
                Cell cell2 = head2.createCell(start++);
                cell2.setCellValue(subHeader);
            }
            // 第一行合并
            sheet.addMergedRegion(new CellRangeAddress(0, 0, colIdx, start -1));
            colIdx = start;
        }
        // ================= 数据 =================
            List<DeliveryRow> data = Lists.newArrayList();
            Map<String, DeliveryRow.Detail> detail = new HashMap<>();
            detail.put("0-3公里", new DeliveryRow.Detail(30, "80%", 10.5, 15.2, "85%", 2.3));
            detail.put("3-5公里", new DeliveryRow.Detail(15, "60%", 12.5, 18.3, "75%", 3.2));
            detail.put("5公里以上", new DeliveryRow.Detail(10, "50%", 15.5, 22.1, "65%", 4.5));
            detail.put("其他", new DeliveryRow.Detail(5, "40%", 18.5, 24.0, "55%", 5.1));
            DeliveryRow row = new DeliveryRow("淘宝闪购", "淘宝闪购汇总", 45, "93.33%", detail);
            DeliveryRow row2 = new DeliveryRow("淘宝闪购", "平台配", 45, "93.33%", detail);
            DeliveryRow row3 = new DeliveryRow("淘宝闪购", "平台配", 45, "93.33%", detail);
            data.add(row); data.add(row2); data.add(row3);

            Map<String, List<DeliveryRow>> channelData = new HashMap<>();
            channelData.put("淘宝闪购", data);

        DeliveryRow row4 = new DeliveryRow("美团", "美团汇总", 45, "93.33%", detail);
        DeliveryRow row5 = new DeliveryRow("美团", "其他", 45, "93.33%", detail);

        channelData.put("美团", Lists.newArrayList(row4, row5));
        int rowIdx = 2;
        for (Map.Entry<String, List<DeliveryRow>> entry : channelData.entrySet()) {
            String channel = entry.getKey();
            int startRow = rowIdx;
            List<DeliveryRow> rows = entry.getValue();
            for (DeliveryRow rowData : rows) {
                Row temppRow = sheet.createRow(startRow++);
                int cellIndex = 0;
                temppRow.createCell(cellIndex ++ ).setCellValue(rowData.getChannel());
                temppRow.createCell(cellIndex ++ ).setCellValue(rowData.getCarrier());
                temppRow.createCell(cellIndex ++ ).setCellValue(rowData.getTotalOrderCnt());
                temppRow.createCell(cellIndex ++ ).setCellValue(rowData.getTotalFulfillRate());
                for (String senHeader : firstRowHeader) {
                    DeliveryRow.Detail mapDetail =  rowData.getDetail().get(senHeader);
                    if (Objects.isNull(mapDetail)) {
                        mapDetail = new  DeliveryRow.Detail();
                    }
                    temppRow.createCell(cellIndex ++ ).setCellValue(mapDetail.getRangeOrderCnt());
                    temppRow.createCell(cellIndex ++ ).setCellValue(mapDetail.getRangeRate());
                    temppRow.createCell(cellIndex ++ ).setCellValue(mapDetail.getAcceptMinutes());
                    temppRow.createCell(cellIndex ++ ).setCellValue(mapDetail.getDeliverMinutes());
                    temppRow.createCell(cellIndex ++ ).setCellValue(mapDetail.getRangeFulfillRate());
                    temppRow.createCell(cellIndex ++ ).setCellValue(mapDetail.getFee());
                }
            }
            sheet.addMergedRegion(new CellRangeAddress(rowIdx, startRow - 1, 0, 0));
            rowIdx = startRow;
        }

        

        // 设置所有单元格样式（前两行为表头样式，其余为内容样式）
        CellStyle contentStyle = createContentStyle(wb);
        CellStyle headerStyle = createHeaderStyle(wb);
        for (Row fr : sheet) {
            CellStyle style = fr.getRowNum() < 2 ? headerStyle : contentStyle;
            for (Cell cell : fr) {
                cell.setCellStyle(style);
            }
        }
        wb.write(os);
        wb.close();
    }



    private static void mergeSameValue(Sheet sheet, int startRow, int col) {
        int lastRow = startRow;
        String lastValue = sheet.getRow(startRow).getCell(col).getStringCellValue();

        for (int i = startRow + 1; i <= sheet.getLastRowNum(); i++) {
            String cur = sheet.getRow(i).getCell(col).getStringCellValue();
            if (!Objects.equals(lastValue, cur)) {
                if (i - lastRow > 1) {
                    sheet.addMergedRegion(
                            new CellRangeAddress(lastRow, i - 1, col, col));
                }
                lastRow = i;
                lastValue = cur;
            }
        }

        if (sheet.getLastRowNum() - lastRow >= 1) {
            sheet.addMergedRegion(
                    new CellRangeAddress(lastRow, sheet.getLastRowNum(), col, col));
        }
    }

    private static CellStyle createHeaderStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();

        // 水平 & 垂直居中
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // 背景色（注意：必须同时设置 FillPattern）
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setWrapText(true);

        // 边框（可选，但强烈建议）
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        // 字体
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);

        return style;
    }

    private static CellStyle createContentStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();

        // 水平 & 垂直居中
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // 背景色（注意：必须同时设置 FillPattern）



        // 字体
        Font font = wb.createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);

        return style;
    }



}
