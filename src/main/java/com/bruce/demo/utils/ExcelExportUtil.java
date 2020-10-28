package com.bruce.demo.utils;

import com.bruce.demo.bean.Products;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bolin
 * @date 2020/7/2 15:28
 * @description:
 */
@Slf4j
public class ExcelExportUtil {
    /**
     * 导出excel
     *
     * @param fileName
     * @param headers
     * @param dataList
     * @param response
     */
    public static void excelExport( String fileName, String[] headers, List<Object[]> dataList,
                                   HttpServletResponse response) {
        Workbook workbook = getWorkbook(headers, dataList);
        if (workbook != null) {
            ByteArrayOutputStream byteArrayOutputStream = null;
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                workbook.write(byteArrayOutputStream);
                String suffix = ".xls";

                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + new String((fileName + suffix).getBytes(), StandardCharsets.ISO_8859_1));

                OutputStream outputStream = response.getOutputStream();
                outputStream.write(byteArrayOutputStream.toByteArray());
                outputStream.close();
            } catch (Exception e) {
                log.error(e.toString());
                throw new RuntimeException(e);
            } finally {
                try {
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                } catch (IOException e) {
                    log.error(e.toString());
                    throw new RuntimeException(e);
                }

                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error(e.toString());
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public static File inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @param headers  列头
     * @param dataList 数据
     * @return Workbook
     */
    private static Workbook getWorkbook(String[] headers, List<Object[]> dataList) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row;
        Cell cell;
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        Font font = workbook.createFont();

        int line = 0, maxColumn = 0;
        // 设置列头
        if (headers != null && headers.length > 0) {
            row = sheet.createRow(line++);
            row.setHeightInPoints(23);
            font.setBold(true);
            font.setFontHeightInPoints((short) 13);
            style.setFont(font);

            maxColumn = headers.length;
            for (int i = 0; i < maxColumn; i++) {
                cell = row.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(style);
            }
        }

        // 渲染数据
        if (dataList != null && dataList.size() > 0) {
            for (int index = 0, size = dataList.size(); index < size; index++) {
                Object[] data = dataList.get(index);
                if (data != null && data.length > 0) {
                    row = sheet.createRow(line++);
                    row.setHeightInPoints(20);

                    int length = data.length;
                    if (length > maxColumn) {
                        maxColumn = length;
                    }

                    for (int i = 0; i < length; i++) {
                        cell = row.createCell(i);
                        cell.setCellValue(data[i] == null ? null : data[i].toString());
                    }
                }
            }
        }

        for (int i = 0; i < maxColumn; i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }


    public static String[] getShopFileHeader() {
        String[] header = new String[6];
        header[0] = "ID";
        header[1] = "商品名称";
        header[2] = "直播间曝光次数";
        header[3] = "直播间点击次数";
        header[4] = "成交订单数";
        header[5] = "成交金额";
        return header;

    }

    public static List<Object[]> convert2InfoData(List<Products> itemList) {
        List<Object[]> dataList = new ArrayList<>();
        if (null == itemList || itemList.size() == 0) {
            return null;
        }

        itemList.forEach(item -> {
            Object[] data = new Object[6];
            data[0] = item.getProduct_id();
            data[1] = item.getTitle();
            data[2] = item.getStats().getWatch();
            data[3] = item.getStats().getClick();
            data[4] = item.getStats().getOrder_num();
            data[5] = BigDecimal.valueOf(item.getStats().getGmv()).divide(BigDecimal.valueOf(100));

            dataList.add(data);

        });
        return dataList;
    }
}
