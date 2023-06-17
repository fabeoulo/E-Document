/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.test;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.Flow;
import com.advantech.model.Pending;
import com.advantech.model.PreAssy;
import com.advantech.model.Worktime;
import com.advantech.model.WorktimeAutouploadSetting;
import com.advantech.quartzJob.StandardTimeUpload;
import com.advantech.service.FlowService;
import com.advantech.service.PendingService;
import com.advantech.service.PreAssyService;
import com.advantech.service.WorktimeAuditService;
import com.advantech.service.WorktimeAutouploadSettingService;
import com.advantech.service.WorktimeService;
import com.advantech.service.WorktimeUploadMesService;
import com.advantech.webservice.port.FlowUploadPort;
import com.advantech.webservice.port.MaterialPropertyUploadPort;
import com.advantech.webservice.port.ModelResponsorUploadPort;
import com.advantech.webservice.port.StandardtimeUploadPort;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import static junit.framework.Assert.*;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author Wei.Cheng
 */
@WebAppConfiguration
@ContextConfiguration(locations = {
    "classpath:servlet-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UploadPortTest {

    private Worktime w;

    //Port for ie
    @Autowired
    private StandardtimeUploadPort standardtimePort;

    //Port for spe
    @Autowired
    private FlowUploadPort flowUploadPort;

    @Autowired
    private ModelResponsorUploadPort modelResponsorUploadPort;

    @Autowired
    private WorktimeService worktimeService;

    @Autowired
    private FlowService flowService;

    @Autowired
    private PreAssyService preAssyService;

    @Autowired
    private MaterialPropertyUploadPort materialPropertyUploadPort;

    @Autowired
    private WorktimeAutouploadSettingService worktimeAutouploadSettingService;

    @Autowired
    private PendingService pendingService;

    @Before
    public void initTestData() {
        w = worktimeService.findByModel("IDP31-215WP25HIC1");
    }

    @Value("${WORKTIME.UPLOAD.INSERT: true}")
    private boolean isInserted;

    @Value("${WORKTIME.UPLOAD.UPDATE: true}")
    private boolean isUpdated;

    @Value("${WORKTIME.UPLOAD.DELETE: true}")
    private boolean isDeleted;

    @Value("${WORKTIME.UPLOAD.SOP: true}")
    private boolean isUploadSop;

    @Value("${WORKTIME.UPLOAD.RESPONSOR: true}")
    private boolean isUploadResponsor;

    @Value("${WORKTIME.UPLOAD.FLOW: true}")
    private boolean isUploadFlow;

    @Value("${WORKTIME.UPLOAD.MATPROPERTY: true}")
    private boolean isUploadMatProp;

//    @Test
//    @Rollback(true)
    public void uploadParamTest() {
        assertTrue(isInserted);
        assertTrue(isUpdated);
        assertFalse(isDeleted);
        assertTrue(isUploadSop);
        assertTrue(isUploadResponsor);
        assertTrue(isUploadFlow);
        assertFalse(isUploadMatProp);
    }

    @Test//216
    @Rollback(true)
    public void testStandardtimeUpload() throws Exception {
        List<Worktime> l = worktimeService.findAll();
        assertNotNull(l.get(0));
        List<WorktimeAutouploadSetting> settings = worktimeAutouploadSettingService.findByPrimaryKeys(39,40);
        standardtimePort.initSettings(settings);
//        standardtimePort.initSettings();

        Worktime w = worktimeService.findByModel("IDK2115N2201-T");
        standardtimePort.update(w);

//        l.forEach((worktime) -> {
//            try {
//                System.out.println("Upload model: " + worktime.getModelName());
//                standardtimePort.update(worktime);
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
//        });
    }

//    @Test//216
    public void testFlowUpload() throws Exception {
        Worktime w = worktimeService.findByPrimaryKey(529);
        Flow f;

        f = flowService.findByFlowName("TEST_T2");
        w.setFlowByTestFlowId(f);
        flowUploadPort.update(w);//insert

        f = flowService.findByFlowName("TEST_T2-T3");
        w.setFlowByTestFlowId(f);
        flowUploadPort.update(w);//update

        f = flowService.findByFlowName("");//f=null
        w.setFlowByTestFlowId(f);
        flowUploadPort.update(w);//delete
    }


//    @Test//216
    public void testWorktimeUploadPort() throws Exception {

        modelResponsorUploadPort.update(w);//216
//        this.testSopUpload();//216
//        this.testFlowUpload();//216
//        materialPropertyUploadPort.update(w);//216
    }

//暫時用
//    @Test//216
    public void testStandardtimeUpload2() throws Exception {
        PageInfo info = new PageInfo();
        info.setSearchField("modifiedDate");
        info.setSearchOper("gt");
        info.setSearchString("2017-11-26");
        info.setRows(Integer.MAX_VALUE);
        List<Worktime> l = worktimeService.findAll(info);

        standardtimePort.initSettings();

        for (Worktime worktime : l) {
            System.out.println(worktime.getModelName());
            standardtimePort.update(worktime);
        }
    }

    //QUARTZ
    @Autowired
    private StandardTimeUpload standardTimeUpload;

    @Test//216
    @Rollback(true)
    public void testStandardTimeUploadJob() {
        standardTimeUpload.uploadToMes();
    }
    
//    @Test//216
    @Rollback(true)
    public void testMaterialPropertyUploadPort() throws Exception {

        Integer[] ids = {529};
        List<Worktime> l = worktimeService.findByPrimaryKeys(ids);
        materialPropertyUploadPort.initSetting();

        for (Worktime worktime : l) {
            materialPropertyUploadPort.update(worktime);
//            materialPropertyUploadPort.delete(worktime);
        }
    }

    @Autowired
    private WorktimeUploadMesService uploadMesService;

    @Test
    @Rollback(true)
    public void testMaterialPropertyUploadPortM6() throws Exception {

        Integer[] ids = {
            1072};
        List<Worktime> l = worktimeService.findByPrimaryKeys(ids);
        Worktime cloneW = (Worktime) BeanUtils.cloneBean(l.get(0));
        cloneW.setId(0); //CloneW is a new row, reset id.
        cloneW.setModelName("IDP31-215WP25HIC8");
        cloneW.setWorktimeFormulaSettings(null);
        cloneW.setBwFields(null);
        cloneW.setCobots(null);
        List<Worktime> ll = new ArrayList();
        ll.add(cloneW);

//        Flow pA = cloneW.getFlowByBabFlowId();
//        int key = pA.getId();
//        Flow ff = flowService.findByPrimaryKey(key);
//        System.out.println(ff.getName());
        uploadMesService.portParamInit();
        uploadMesService.update(cloneW);

        int i = 1;
        for (Worktime w : ll) {
//            uploadMesService.insert(w);
            materialPropertyUploadPort.insert(w);
//            flowUploadPort.insert(w);
        }

        materialPropertyUploadPort.initSetting();

        for (Worktime worktime : l) {
            materialPropertyUploadPort.update(worktime);
        }

//        materialPropertyUploadPort.update(worktime);
//        materialPropertyUploadPort.delete(worktime);
    }


//    @Test
    @Rollback(true)
    public void testModelResponsorUploadPort() throws Exception {
        Worktime obj = new Worktime();
        obj.setModelName("TEST-MODEL-2");
        modelResponsorUploadPort.delete(obj);
    }
}
