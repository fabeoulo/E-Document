/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.controller.db2;

import com.advantech.converter.CobotConverter;
import com.advantech.excel.JxlsExcelView;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.SheetView;
import com.advantech.model.db2.WorktimeM4f;
import com.advantech.service.SheetViewService;
import com.advantech.service.db2.WorktimeAuditM4fService;
import com.advantech.service.db2.WorktimeM4fService;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Wei.Cheng
 */
@Controller
@RequestMapping("/WorktimeDownloadM4f")
public class FileDownloadM4fController {

    private static final Logger logger = LoggerFactory.getLogger(FileDownloadM4fController.class);
//
//    @Autowired
    private SheetViewService sheetViewService;
//
    @Autowired
    private WorktimeM4fService worktimeService;

    @Autowired
    private WorktimeAuditM4fService auditService;

//    @Autowired
    private CobotConverter cobotConverter;
//    
    private static int fileDownloadCount = 0;

    @ResponseBody
    @RequestMapping(value = "/excel", method = {RequestMethod.GET})
    protected ModelAndView generateExcel(PageInfo info) {
        // create some sample data
        info.setRows(-1);
        info.setSidx("id");
        info.setSord("asc");
        info.setPage(1); //Override the request param from jqgrid.

        List<SheetView> l = sheetViewService.findAll(info);
        ModelAndView mav = new ModelAndView("ExcelRevenueSummary");
        mav.addObject("revenueData", l);
        return mav;
    }
    
    @ResponseBody
    @RequestMapping(value = "/excel2", method = {RequestMethod.GET})
    protected ModelAndView generateExcel2(PageInfo info) throws Exception {
        logger.info("Excel download count " + (++fileDownloadCount));
        return this.fileExport("worktime-template-m4f.xls", info);
    }

    @ResponseBody
    @RequestMapping(value = "/excel3", method = {RequestMethod.GET})
    protected ModelAndView generateExcel3(PageInfo info) throws Exception {
        logger.info("Excel download count " + (++fileDownloadCount));
        return this.fileExport("worktime-PMC-m4f.xls", info);
    }

    @ResponseBody
    @RequestMapping(value = "/excelForSpe", method = {RequestMethod.GET})
    protected ModelAndView generateExcelForUpload(PageInfo info) throws Exception {
        logger.info("Excel download count " + (++fileDownloadCount));
        return this.fileExport("Plant-sp matl status(M3) (2).xls", info);
    }

    private ModelAndView fileExport(String tempfileName, PageInfo info) throws Exception {

        //Adjust search query and search data
        info.setRows(-1);
        info.setSidx("id");
        info.setSord("asc");
        info.setPage(1); //Prevent select query jump to page 2 bug.

        //Do nothing when empty result
        List<WorktimeM4f> l = worktimeService.findWithFullRelation(info);
        if (l.isEmpty()) {
            throw new Exception("The query result is empty.");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Number revisionNum = auditService.findLastRevisions();
        String revisionInfo = this.encodeRevisionInfo(revisionNum);

        Map<String, Object> model = new HashMap<>();
        model.put("worktimes", l);
        model.put("dateFormat", dateFormat);
        model.put("revision", revisionInfo);
//        model.put("cobotConverter", cobotConverter);

        return new ModelAndView(new JxlsExcelView("excel-template/" + tempfileName, tempfileName), model);

    }

    private String encodeRevisionInfo(Number revisionNumber) {
        String encodeString = "revision: " + revisionNumber;
        byte[] bytesEncoded = Base64.encodeBase64(encodeString.getBytes());
        return new String(bytesEncoded);
    }
}
