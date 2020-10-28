package com.bruce.demo.controller;

import com.bruce.demo.bean.Products;
import com.bruce.demo.utils.ExcelExportUtil;
import com.bruce.demo.utils.JsonSupport;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lql
 * @version: 2020/10/27 11:58 下午
 */
@Slf4j
@RestController
@Api(value = "Excel 相关")
@RequestMapping("api/excel")
@RequiredArgsConstructor
public class ExcelExportController implements JsonSupport {

    private String path = System.getProperty("user.dir");
    TypeReference<Map<String, Object>> MAP_STRING_OBJECT = new TypeReference<Map<String, Object>>() {
    };

    @PostMapping("/export")
    public void excelExport(MultipartFile multipartFile, HttpServletResponse response) {
        if (!multipartFile.isEmpty()) {

            String fileName = System.nanoTime() + "-data";

            try {
                InputStream inputStream = multipartFile.getInputStream();

                InputStreamReader isreader = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(isreader);
                List<Products> detailInfoList = covert(br.readLine(), new HashMap<>());
                List<Object[]> dataList = ExcelExportUtil.convert2InfoData(detailInfoList);
                String[] header = ExcelExportUtil.getShopFileHeader();
                ExcelExportUtil.excelExport(fileName, header, dataList, response);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }


    private List<Products> covert(String fileString, Map<String, Object> map) {
        map = json2object(fileString, MAP_STRING_OBJECT, Collections::emptyMap, "");
        if (!CollectionUtils.isEmpty(map)) {
            Object data = map.get("data");
            String products = object2json(((Map) data).get("products"), "");
            return json2object(products, new TypeReference<List<Products>>() {
            }, null, "");

        }
        return Collections.emptyList();
    }

}
