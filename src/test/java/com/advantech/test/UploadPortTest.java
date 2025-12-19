/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.test;

import com.advantech.helper.HibernateObjectPrinter;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.Flow;
import com.advantech.model.PreAssy;
import com.advantech.model.Worktime;
import com.advantech.model.WorktimeAutouploadSetting;
import com.advantech.model.WorktimeMaterialPropertyUploadSetting;
import com.advantech.service.FlowService;
import com.advantech.service.PendingService;
import com.advantech.service.PreAssyService;
import com.advantech.service.WorktimeAutouploadSettingService;
import com.advantech.service.WorktimeMaterialPropertyUploadSettingService;
import com.advantech.service.WorktimeService;
import com.advantech.webservice.port.FlowUploadPort;
import com.advantech.webservice.port.MaterialPropertyUploadPort;
import com.advantech.webservice.port.ModelResponsorUploadPort;
import com.advantech.webservice.port.MtdTestIntegrityQueryPort;
import com.advantech.webservice.port.MtdTestIntegrityUploadPort;
import com.advantech.webservice.port.StandardtimeUploadPort;
import java.math.BigDecimal;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.transaction.Transactional;
import static org.junit.Assert.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private List<Worktime> worktimes;

    //Port for ie
    @Autowired
    private StandardtimeUploadPort standardtimePort;

    //Port for spe
    @Autowired
    private FlowUploadPort flowUploadPort;

    @Autowired
    private ModelResponsorUploadPort mappingUserPort;

//    @Autowired
//    private SopUploadPort sopPort;
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

    @Autowired
    private MtdTestIntegrityUploadPort mtdTestIntegrityUploadPort;

    @Autowired
    private MtdTestIntegrityQueryPort mtdTestIntegrityQueryPort;

    @Autowired
    private WorktimeMaterialPropertyUploadSettingService propService;

    @Before
    public void initTestData() {
        w = worktimeService.findByModel("SPC618WEPUD75AE-ES");
//        worktimes = worktimeService.findAll();
//        worktimes = newArrayList(w);
//        worktimes = worktimes.stream().filter(o -> o.getTwm2Flag() == 1).collect(toList());
    }

    @Value("${WORKTIME.UPLOAD.INSERT: true}")
    private boolean isInserted;

    @Value("${WORKTIME.UPLOAD.UPDATE: true}")
    private boolean isUpdated;

    @Value("${WORKTIME.UPLOAD.DELETE: false}")
    private boolean isDeleted;

    @Value("${WORKTIME.UPLOAD.RESPONSOR: true}")
    private boolean isUploadResponsor;

    @Value("${WORKTIME.UPLOAD.FLOW: true}")
    private boolean isUploadFlow;

    @Value("${WORKTIME.UPLOAD.MATPROPERTY: true}")
    private boolean isUploadMatProp;

    @Test
//    @Rollback(true)
    public void uploadParamTest() {
        assertTrue(isInserted);
        assertTrue(isUpdated);
        assertFalse(isDeleted);
        assertTrue(isUploadResponsor);
        assertTrue(isUploadFlow);
        assertTrue(isUploadMatProp);
    }

//    @Test//245
//    @Rollback(true)
    public void testStandardtimeUpload() throws Exception {
//        List<Worktime> l = worktimeService.findAll();
        List<Worktime> l = worktimeService.findWithFlowRelationAndCobot(17372);
        assertNotNull(l);
        List<WorktimeAutouploadSetting> settings = worktimeAutouploadSettingService.findByPrimaryKeys(19, 20, 21, 22);
        standardtimePort.initSettings(settings);

        for (Worktime worktime : l) {
            System.out.println("Upload model: " + worktime.getModelName());
//            worktime.setAssy(new BigDecimal(26));
//            worktime.setReasonCode("A6");
            standardtimePort.update(worktime);
        }
    }

//    @Test//245
    @Rollback(true)
    public void testFlowUpload() throws Exception {
        w = worktimeService.findByModel("EKI-9516-P0IDH10E-TEST");
        Flow f = flowService.findByPrimaryKey(356);
        w.setFlowByTestFlowId(f);
//        flowUploadPort.update(w);

        f = flowService.findByPrimaryKey(355);
        w.setFlowByBabFlowId(f);
//        flowUploadPort.update(w);

        PreAssy pf = preAssyService.findByPrimaryKey(10);
        w.setPreAssy(pf);
        flowUploadPort.update(w);

//        List<Worktime> l = worktimes;
//        for (Worktime worktime : l) {
//            System.out.println("Upload model: " + worktime.getModelName());
//            flowUploadPort.update(worktime);
//        }
    }

//    @Test//245
    public void testPartMappingUserUpload() throws Exception {
        w = worktimeService.findByModel("EKI-9516-P0IDH10E-TEST");
        mappingUserPort.update(w);

        List<Worktime> l = this.worktimes;
        l = l.stream().filter(w -> w.getUserBySpeOwnerId().getId() == 26).collect(toList());
        assertEquals(l.size(), 1003);
        l.forEach((worktime) -> {
            try {
                System.out.println("Upload model: " + worktime.getModelName());
                mappingUserPort.update(worktime);
//                mappingUserPort.insert(worktime);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

//    @Test//245 // sopPort won't use anymore.
//    public void testSopUpload() throws Exception {
////        w = worktimeService.findByModel("2063002307");
////        w.setAssyPackingSop("M-07-TP2397");
////        sopPort.update(w);
//
////        w.setAssyPackingSop(null);
////        w.setTestSop(null);
////        sopPort.update(w);
//
//        List<Worktime> l = this.worktimes;
//        for (Worktime worktime : l) {
//            System.out.println("Upload " + worktime.getModelName());
//            sopPort.update(worktime);
//        }
//    }
    //暫時用
//    @Test
    public void testStandardtimeUploadAll() throws Exception {
        standardtimePort.initSettings();
//        standardtimePort.update(w);

        PageInfo info = new PageInfo();
        info.setSearchField("modifiedDate");
        info.setSearchOper("gt");
        info.setSearchString("2017-11-26");
        info.setRows(Integer.MAX_VALUE);
        List<Worktime> l = worktimeService.findAll(info);

        for (Worktime worktime : l) {
            System.out.println(worktime.getModelName());
            standardtimePort.update(worktime);
        }
    }

//    @Test
    public void testStandardtimeDelete() throws Exception {
//        PageInfo info = new PageInfo();
//        info.setSearchField("modifiedDate");
//        info.setSearchOper("gt");
//        info.setSearchString("2017-11-26");
//        info.setRows(Integer.MAX_VALUE);
//        List<Worktime> l = worktimeService.findAll(info);
//
//        List<WorktimeAutouploadSetting> settings = worktimeAutouploadSettingService.findByPrimaryKeys(29, 31);
//        standardtimePort.initSettings(settings);
////        standardtimePort.delete(w);
//
//        l = l.stream().filter(w -> w.getCobots().isEmpty()
//                || !w.getCobots().stream().anyMatch(c -> c.getName().contains("ADAM"))
//        ).collect(toList());
////        for (Worktime worktime : l) {
////            System.out.println(worktime.getModelName());
////            standardtimePort.delete(worktime);
////        }
    }

//    @Test//245
//    @Rollback(true)
    public void testMaterialPropertyUploadPort() throws Exception {
//        Worktime w = worktimeService.findByModel("EKI-9516-P0IDH10E-TEST");

        List<Worktime> l = worktimeService.findWithFlowRelation();
        List<WorktimeMaterialPropertyUploadSetting> settings = propService.findByPrimaryKeys(73);
//        assertEquals(5, settings.size());
        materialPropertyUploadPort.initSettings(settings);
//        materialPropertyUploadPort.update(w);

//        List<WorktimeMaterialPropertyUploadSetting> settings = propService.findAll();
//        materialPropertyUploadPort.initSettings(settings);
        for (Worktime worktime : l) {
            System.out.println("Upload " + worktime.getModelName());
            materialPropertyUploadPort.update(worktime);
        }
//        materialPropertyUploadPort.update(worktime);
//        materialPropertyUploadPort.delete(worktime);
    }

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory factory;

//    @Test
    @Rollback(true)
    public void testStandardTimeUpload() throws Exception {
        Session session = factory.getCurrentSession();
        List<Worktime> l = session.createCriteria(Worktime.class).add(Restrictions.like("modelName", "BB", MatchMode.START)).list();
        assertEquals(510, l.size());

        List<WorktimeAutouploadSetting> settings = worktimeAutouploadSettingService.findByPrimaryKeys(2, 4);
        standardtimePort.initSettings(settings);
        l.forEach((worktime) -> {
            try {
                worktime.setReasonCode("A0");
                System.out.println(worktime.getModelName());
                standardtimePort.update(worktime);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

//    @Test//245
//    @Rollback(true)
    public void testmtdTestIntegrityUpload() throws Exception {
        List<Worktime> l = worktimeService.findByModelNames("HPC190A2301-T", "HPC612050ZAMAT3-ES");
        l.forEach((worktime) -> {
            try {
                mtdTestIntegrityUploadPort.update(worktime);

                HibernateObjectPrinter.print(worktime.getModelName());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
    }
}
