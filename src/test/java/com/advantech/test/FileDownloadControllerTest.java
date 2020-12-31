/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.test;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.SheetView;
import com.advantech.model.Worktime;
import com.advantech.service.WorktimeService;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 *
 * @author Wei.Cheng
 */
//@WebAppConfiguration
//@ContextConfiguration(locations = {
//    "classpath:servlet-context.xml",
//    "classpath:hibernate.cfg.xml"
//})
//@RunWith(SpringJUnit4ClassRunner.class)
public class FileDownloadControllerTest {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private WorktimeService worktimeService;

//    @Test
    public void testFileDownloadFromTemp() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(get("/Worktime/excel2").accept("application/json"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andDo(MockMvcResultHandlers.print());

    }

//    @Test
    public void testAddDataToTemp() throws Exception {
        Resource r = resourceLoader.getResource("classpath:excel-template\\Plant-sp matl status(M3).xls");
        try (InputStream is = r.getInputStream()) {
            List<SheetView> l = worktimeService.findSheetView();
            try (OutputStream os = new FileOutputStream("C:\\Users\\Wei.Cheng\\Desktop\\object_collection_output.xls")) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Context context = new Context();
                context.putVar("sheetViews", l);
                context.putVar("dateFormat", dateFormat);

                Transformer transformer = TransformerFactory.createTransformer(is, os);
                JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
                evaluator.getJexlEngine().setSilent(false);

                JxlsHelper helper = JxlsHelper.getInstance();
                helper.processTemplate(context, transformer);
//                .processTemplate(is, os, context);
            }
        }
    }

//    @Test
    public void testAddDataToTemp2() throws Exception {
        Resource r = resourceLoader.getResource("classpath:excel-template\\worktime-template.xls");
        try (InputStream is = r.getInputStream()) {
            
            PageInfo info = new PageInfo();
            info.setSearchField("modelName");
            info.setSearchString("testing-do-not-remove");
            info.setSearchOper("eq");
            
            List<Worktime> l = worktimeService.findWithFullRelation(info);
            assertEquals(1, l.size());
            
            try (OutputStream os = new FileOutputStream("C:\\Users\\Wei.Cheng\\Desktop\\worktime_output.xls")) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

                Context context = new Context();
                context.putVar("worktimes", l);
                context.putVar("dateFormat", dateFormat);

                Transformer transformer = TransformerFactory.createTransformer(is, os);
                JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
                evaluator.getJexlEngine().setSilent(true);

                JxlsHelper helper = JxlsHelper.getInstance();
                helper.processTemplate(context, transformer);
            }
        }
    }

}
