/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.controller;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.WorktimeExtra;
import com.advantech.service.WorktimeExtraService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Justin.Yeh
 */
@Controller
@RequestMapping("/Excel")
public class ExcelController {

    @Autowired
    private WorktimeExtraService worktimeExtraService;

    @ResponseBody
    @RequestMapping(value = "/downloadWorktimeExtra", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> generateExcel(PageInfo info) throws Exception {
        //Adjust search query and search data
        info.setRows(-1);
        info.setSidx("id");
        info.setSord("asc");
        info.setPage(1); //Prevent select query jump to page 2 bug.

        List<WorktimeExtra> datas = worktimeExtraService.findWithFullRelation(info);

        ByteArrayOutputStream out = this.generateReport(datas);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=worktimeExtra.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(out.toByteArray());
    }

    private ByteArrayOutputStream generateReport(List<WorktimeExtra> data) throws IOException {

        ClassPathResource resource = new ClassPathResource("excel-template/worktimeExtra_template.xlsx");
        try ( InputStream is = resource.getInputStream();  Workbook workbook = new XSSFWorkbook(is);  ByteArrayOutputStream out = new ByteArrayOutputStream();) {

            Sheet sheet = workbook.getSheetAt(0);

            // 從第2列開始填資料 (假設第1列是標題)
            int rowIndex = 1;
            for (WorktimeExtra item : data) {
                Row row = sheet.createRow(rowIndex++);

                Cell cell = row.createCell(0);
                cell.setCellValue(item.getId());
                cell = row.createCell(1);
                cell.setCellValue(item.getWorkCenter());
                cell = row.createCell(2);
                cell.setCellValue(item.getWorktime().getModelName());
                cell = row.createCell(3);
                cell.setCellValue(item.getProcess());
                cell = row.createCell(4);
                cell.setCellValue(item.getWorktimeAutouploadSetting().getColumnName());
                cell = row.createCell(5);
                cell.setCellValue(item.getItem());
                cell = row.createCell(6);
                cell.setCellValue(item.getExtraTime().doubleValue());
            }

            workbook.write(out);
            return out;
        }
    }

}
