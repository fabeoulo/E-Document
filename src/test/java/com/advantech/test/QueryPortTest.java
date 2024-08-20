/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.test;

import com.advantech.helper.HibernateObjectPrinter;
import com.advantech.model.Worktime;
import com.advantech.model.db2.WorktimeM4f;
import com.advantech.model.WorktimeMaterialPropertyUploadSetting;
import com.advantech.service.db2.WorktimeM4fService;
import com.advantech.service.WorktimeMaterialPropertyUploadSettingService;
import com.advantech.service.WorktimeService;
import com.advantech.webservice.Factory;
import com.advantech.webservice.port.StandardWorkReasonQueryPort;
import com.advantech.webservice.port.FlowRuleQueryPort;
import com.advantech.webservice.port.MaterialFlowQueryPort;
import com.advantech.webservice.port.MaterialPropertyQueryPort;
import com.advantech.webservice.port.MaterialPropertyValueQueryPort;
import com.advantech.webservice.port.MaterialPropertyUserPermissionQueryPort;
import com.advantech.webservice.port.MesUserInfoQueryPort;
import com.advantech.webservice.port.ModelResponsorQueryPort;
import com.advantech.webservice.port.MtdTestIntegrityQueryPort;
import com.advantech.webservice.port.SopQueryPort;
import com.advantech.webservice.port.StandardWorkTimeQueryPort;
import com.advantech.webservice.root.Section;
import com.advantech.webservice.unmarshallclass.StandardWorkReason;
import com.advantech.webservice.unmarshallclass.FlowRule;
import com.advantech.webservice.unmarshallclass.MaterialFlow;
import com.advantech.webservice.unmarshallclass.MaterialProperty;
import com.advantech.webservice.unmarshallclass.MaterialPropertyUserPermission;
import com.advantech.webservice.unmarshallclass.MaterialPropertyValue;
import com.advantech.webservice.unmarshallclass.MtdTestIntegrity;
import com.advantech.webservice.unmarshallclass.SopInfo;
import com.advantech.webservice.unmarshallclass.StandardWorkTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.Assert.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
//    "classpath:hibernate.cfg.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class QueryPortTest {

    @Autowired
    private SopQueryPort sopQueryPort;

    @Autowired
    private ModelResponsorQueryPort mrQueryPort;

    @Autowired
    private MesUserInfoQueryPort mesUserQueryPort;

    @Autowired
    private FlowRuleQueryPort flowRuleQueryPort;

    @Autowired
    private MaterialFlowQueryPort materialFlowQueryPort;

    @Autowired
    private MaterialPropertyUserPermissionQueryPort materialPropertyUserPermissionQueryPort;

    @Autowired
    private MaterialPropertyQueryPort materialPropertyQueryPort;

    @Autowired
    private MaterialPropertyValueQueryPort materialPropertyValueQueryPort;

    @Autowired
    private StandardWorkReasonQueryPort errorGroupQueryPort;

    @Autowired
    private WorktimeService worktimeService;

    private Worktime w;

    @Autowired
    private WorktimeM4fService worktimeM4fService;

    private WorktimeM4f wM;

    @Autowired
    private StandardWorkTimeQueryPort worktimeQueryPort;

    @Autowired
    private MtdTestIntegrityQueryPort mtdTestIntegrityQueryPort;

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void init() {
        w = worktimeService.findByPrimaryKey(16894);
        wM = worktimeM4fService.findByModel("AIM-68H-202000");
    }

//    @Test // OK
    public void testSopQueryPort() throws Exception {
        //M9-201
        sopQueryPort.setTypes("T1");
        List<SopInfo> l = sopQueryPort.queryM(wM, Factory.TWM9);
        l.removeIf(s -> "".equals(s.getSopName()) || s.getSopName().contains(","));

//        //M3f-245
//        sopQueryPort.setTypes("T1");
//        List<SopInfo> l0 = sopQueryPort.query(w);
//        List<SopInfo> l = sopQueryPort.queryM(w, Factory.TWM3);
//        l.removeIf(s -> "".equals(s.getSopName()) || s.getSopName().contains(","));
////        assertEquals(1, l.size());
////        l = l.stream().sorted(Comparator.comparing(SopInfo::getSopName)).distinct().collect(toList());
////        l.forEach(s -> {
////            System.out.println(s.getSopName());
////        });
        System.out.println(l.stream().sorted(Comparator.comparing(SopInfo::getSopName)).distinct().map(n -> n.getSopName()).collect(Collectors.joining(",")));
    }

//    @Test // OK
    public void testModelResponsorQueryPort() throws Exception {
        //M9-201
        List l9 = mrQueryPort.queryM(wM, Factory.TWM9);
        assertEquals(5, l9.size());

        HibernateObjectPrinter.print(l9);

        //M3f-245
        List l0 = mrQueryPort.query(w);
        List l = mrQueryPort.queryM(w, Factory.TWM3);
        assertEquals(4, l.size());

        HibernateObjectPrinter.print(l);
    }

//    @Test // OK
    public void testMesUserInfoQueryPort() throws Exception {
        //M9-201
        List l = mesUserQueryPort.queryM(wM, Factory.TWM9);

        //M3f-245
//        List l0 = mesUserQueryPort.query(w);
//        List l = mesUserQueryPort.queryM(w, Factory.TWM3);
        assertEquals(4, l.size());
        HibernateObjectPrinter.print(l);

//        Map m = mesUserQueryPort.transformData(w);
//        HibernateObjectPrinter.print(m);
    }

//    @Test // OK
    public void testFlowRuleQueryPort() throws Exception {
//        //M3f-245
        FlowRule rule0 = flowRuleQueryPort.query("B", "BAB_ASSY-BI");
        FlowRule rule = flowRuleQueryPort.queryM("B", "BAB_ASSY-BI", Factory.TWM3);
        assertNotNull(rule);
        HibernateObjectPrinter.print(rule);

//        //M9-201
//        FlowRule rule = flowRuleQueryPort.queryM("B", "BAB_ASSY-T0-T1-BI", Factory.TWM9);
//        assertNotNull(rule);
//        HibernateObjectPrinter.print(rule);
    }

//    @Test // OK
    public void testMateriaFlowQueryPort() throws Exception {
        //M9-201
        List<MaterialFlow> l9 = materialFlowQueryPort.queryM(wM, Factory.TWM9);
        assertEquals(4, l9.size());
        HibernateObjectPrinter.print(l9);

//        //M3f-245
//        Map m = materialFlowQueryPort.transformData(w);
//        assertEquals(4, m.size());
//        HibernateObjectPrinter.print(m);
//
        List<MaterialFlow> l = materialFlowQueryPort.queryM(w, Factory.TWM3);
        List<MaterialFlow> l0 = materialFlowQueryPort.query(w);
        assertEquals(4, l.size());
        HibernateObjectPrinter.print(l);
//
//        assertEquals(34200, l.get(0).getId());
//        assertEquals(14360, l.get(0).getItemId());
//        assertEquals(719, l.get(0).getFlowRuleId());
    }

//    @Test // OK
    public void testMaterialPropertyUserPermissionQueryPort() throws Exception {
        //M9-201
        List<MaterialPropertyUserPermission> l = materialPropertyUserPermissionQueryPort.queryM("SYSTEM", Factory.TWM9);
        assertEquals("01", l.get(0).getMaterialPropertyNo());
        assertEquals(165, l.size());

//        //M3f-245
//        List<MaterialPropertyUserPermission> l = materialPropertyUserPermissionQueryPort.query("SYSTEM");
//        List<MaterialPropertyUserPermission> l0 = materialPropertyUserPermissionQueryPort.queryM("SYSTEM",Factory.TWM3);
//        assertEquals("01", l.get(0).getMaterialPropertyNo());
//        assertEquals(154, l.size());
    }

//    @Test // OK
    public void testMaterialPropertyQueryPort() throws Exception {
        //M9-201
        List<MaterialProperty> l = materialPropertyQueryPort.queryM("FC", Factory.TWM9);
        assertEquals(1, l.size());
        assertEquals("FC", l.get(0).getMatPropertyNo());
        System.out.println(l.get(0).getAffPropertyType());

//        //M3f-245
//        List<MaterialProperty> l0 = materialPropertyQueryPort.query("FC");
//        List<MaterialProperty> l = materialPropertyQueryPort.queryM("FC",Factory.TWM3);
//        assertEquals(1, l.size());
//        assertEquals("FC", l.get(0).getMatPropertyNo());
//        System.out.println(l.get(0).getAffPropertyType());
    }

    @Autowired
    private WorktimeMaterialPropertyUploadSettingService propSettingService;

    @Test // OK
    public void testListAllSame() throws Exception {
        List<WorktimeMaterialPropertyUploadSetting> settings = propSettingService.findAll();
        Set<String> localMatPropNo = settings.stream()
                .map(WorktimeMaterialPropertyUploadSetting::getMatPropNo)
                .collect(Collectors.toSet());

////        Worktime w1 = worktimeService.findByModel("UTC-532CH-P00E");
//        List<MaterialPropertyValue> remotePropSettings = materialPropertyValueQueryPort.queryM(w,Factory.TWM3);
//        //Find setting not in local
//        List<MaterialPropertyValue> propNotSettingInLocal = remotePropSettings.stream()
//                .filter(r -> Objects.equals(w.getModelName(), r.getItemNo()) && !localMatPropNo.contains(r.getMatPropertyNo()))
//                .collect(Collectors.toList());
//        boolean check = remotePropSettings.containsAll(propNotSettingInLocal);
//        HibernateObjectPrinter.print(check);
        //Find M9 setting not in local
        List<MaterialPropertyValue> remotePropSettings = materialPropertyValueQueryPort.queryM(wM, Factory.TWM9);
        List<MaterialPropertyValue> propSettingInLocal = remotePropSettings.stream()
                .filter(r -> Objects.equals(wM.getModelName(), r.getItemNo()) && !localMatPropNo.contains(r.getMatPropertyNo()))
                .collect(Collectors.toList());
        HibernateObjectPrinter.print(propSettingInLocal);

    }

//    @Test // OK
    public void testMaterialPropertyValueQueryPort() throws Exception {
        //M9-201
        List<MaterialPropertyValue> l9 = materialPropertyValueQueryPort.queryM(wM, Factory.TWM9);
        assertEquals(15, l9.size());
        MaterialPropertyValue value9 = l9.stream()
                .filter(v -> "BP".equals(v.getMatPropertyNo())).findFirst().orElse(null);
        assertNotNull(value9);
        assertEquals("", value9.getAffPropertyValue());
        assertEquals("2", value9.getValue());

        //M3f-245
//        List<MaterialPropertyValue> l0 = materialPropertyValueQueryPort.query(w);
        List<MaterialPropertyValue> l = materialPropertyValueQueryPort.queryM(w, Factory.TWM3);
        assertEquals(29, l.size());
        MaterialPropertyValue value = l.stream()
                .filter(v -> "BD".equals(v.getMatPropertyNo())).findFirst().orElse(null);
        assertNotNull(value);
        assertEquals("", value.getAffPropertyValue());
        assertEquals("40", value.getValue());
    }

//    @Test
    @Rollback(false)
    public void testRetriveMatPropertyValue() throws Exception {
        Session session = sessionFactory.getCurrentSession();

        List<Worktime> worktimes = worktimeService.findAll();

        for (Worktime worktime : worktimes) {

            System.out.println("Update " + worktime.getModelName());
            List<MaterialPropertyValue> l = materialPropertyValueQueryPort.query(worktime);
            MaterialPropertyValue macV = l.stream().filter(m1 -> "MO".equals(m1.getMatPropertyNo())).findFirst().orElse(null);

            if (macV != null) {
                String value = macV.getValue();
                String[] i = value.split(";");
                int len = (int) Arrays.stream(i).filter(o -> !"".equals(o)).count();
                worktime.setMacPrintedQty(len);

            } else {
                worktime.setMacPrintedQty(0);
            }

            session.merge(worktime);

        }

    }

//    @Test
//    @Rollback(false)
    public void updateAffValue() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        List<Worktime> worktimes = worktimeService.findAll();
        for (Worktime worktime : worktimes) {
            System.out.println("Update " + worktime.getModelName());
            List<MaterialPropertyValue> l = materialPropertyValueQueryPort.query(worktime);
            MaterialPropertyValue weight = l.stream().filter(we -> "IW".equals(we.getMatPropertyNo())).findFirst().orElse(null);
            if (weight != null) {
                String affRemote = weight.getAffPropertyValue();
                if (NumberUtils.isNumber(affRemote)) {
                    worktime.setWeightAff(new BigDecimal(affRemote));
                } else {
                    worktime.setWeightAff(BigDecimal.ZERO);
                }
                session.merge(worktime);
            } else {
                System.out.println("IW not found");
            }
        }
    }

//    @Test
    @Rollback(false)
    public void testRetriveMatPropertyValue2() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        List<Worktime> worktimes = worktimeService.findAll();
        for (Worktime worktime : worktimes) {
            List<MaterialPropertyValue> l = materialPropertyValueQueryPort.query(worktime);
            MaterialPropertyValue bq = l.stream().filter(m1 -> "BQ".equals(m1.getMatPropertyNo())).findFirst().orElse(null);
            if (bq != null) {
                System.out.printf("ModelName: %s, BQ-Value: %s \n", worktime.getModelName(), bq.getValue());
                worktime.setBurnInQuantity(Integer.parseInt(bq.getValue()));
            } else {
                worktime.setBurnInQuantity(0);
            }
            session.merge(worktime);
        }
    }

//    @Test // OK
    public void testErrorGroupQueryPort() throws Exception {
        //M9-201
        List<StandardWorkReason> l = errorGroupQueryPort.queryM(Factory.TWM9);
        assertTrue(l != null);
        assertEquals(11, l.size());

//        //M3f-245
//        List<StandardWorkReason> l0 = errorGroupQueryPort.query();
//        List<StandardWorkReason> l = errorGroupQueryPort.queryM(Factory.TWM3);
//        assertTrue(l != null);
//        assertEquals(9, l.size());
        StandardWorkReason eg = l.get(0);

        assertEquals("A0", eg.getId());
        assertEquals("預估工時修正", eg.getName());

        HibernateObjectPrinter.print(l);
    }

//    @Test // OK
    public void testStandardWorkTimeQueryPort() throws Exception {
        //M9-201
        List<StandardWorkTime> l = this.worktimeQueryPort.queryM(wM.getModelName(), Section.BAB.getCode(), Factory.TWM9);
        HibernateObjectPrinter.print(l);
        l = this.worktimeQueryPort.queryM(wM.getModelName(), Factory.TWM9);
        HibernateObjectPrinter.print(l);

//        //M3f-245
//        List<StandardWorkTime> l0 = this.worktimeQueryPort.query(w.getModelName(), Section.BAB.getCode());
//        List<StandardWorkTime> l = this.worktimeQueryPort.queryM(w.getModelName(), Section.BAB.getCode(),Factory.TWM3);
////        l = l.stream().filter(s -> s.getITEMNO().equals("IMC-450-SL")).collect(toList());
//
//        HibernateObjectPrinter.print(l);
//        l = this.worktimeQueryPort.query(w.getModelName());
//        HibernateObjectPrinter.print(l);
    }

    @Test // OK
    public void testMtdTestIntegrityQueryPort() throws Exception {
        //M9-201
        List<MtdTestIntegrity> l9 = this.mtdTestIntegrityQueryPort.queryM(wM, Factory.TWM9);

        assertEquals(3, l9.size());
        assertEquals("T2", l9.get(1).getStationName());
        assertEquals("PT1", l9.get(0).getStationName());

        //M3f-245
        w = worktimeService.findByPrimaryKey(4653);
        List<MtdTestIntegrity> l0 = this.mtdTestIntegrityQueryPort.query(w);
        List<MtdTestIntegrity> l = this.mtdTestIntegrityQueryPort.queryM(w, Factory.TWM3);

        assertEquals(2, l.size());
        assertEquals("T2", l.get(1).getStationName());
        assertEquals("系統", l.get(0).getUserName());
    }

}
