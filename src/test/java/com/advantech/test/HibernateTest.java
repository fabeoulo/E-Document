/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.test;

import com.advantech.helper.HibernateObjectPrinter;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.Flow;
import com.advantech.model.Pending;
import com.advantech.model.Worktime;
import com.advantech.model.WorktimeLevelSetting;
import com.advantech.model.WorktimeMaterialPropertyUploadSetting;
import com.advantech.service.WorktimeAuditService;
import com.advantech.service.WorktimeLevelSettingService;
import com.advantech.service.WorktimeMaterialPropertyUploadSettingService;
import com.advantech.service.WorktimeService;
import com.advantech.service.WorktimeUploadMesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.Validator;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.joda.time.DateTime;
import static org.junit.Assert.*;
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
})
@RunWith(SpringJUnit4ClassRunner.class)
public class HibernateTest {

    @Autowired
    private WorktimeService worktimeService;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private WorktimeAuditService auditService;

    @Autowired
    private Validator validator;

    @Autowired
    private WorktimeUploadMesService worktimeUploadMesService;

    @Autowired
    private WorktimeLevelSettingService worktimeLevelSettingService;

    @Autowired
    private WorktimeMaterialPropertyUploadSettingService propSettingService;

    @Before
    public void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
    }

//    @Test
    @Transactional
    @Rollback(false)
    public void testWorktimeMaterialPropertyUploadSettingService() {
        List<WorktimeMaterialPropertyUploadSetting> settings = propSettingService.findAll();
    }

//    @Test
    @Transactional
    @Rollback(false)
    public void testWorktimeInitUnfilledFormulaColumn() {
        Worktime w = worktimeService.findByModel("9666J43UA01-TEST");
        HibernateObjectPrinter.print(w.getPending().getId());
        worktimeService.initUnfilledFormulaColumn(w);
    }

//    @Test
//    @Transactional
//    @Rollback(false)
    public void testLevelSettingService() {
        List<Worktime> w = worktimeService.findAll();
//        List<String> models = w.stream().map(wt -> wt.getModelName()).collect(Collectors.toList());
        w.forEach(n -> {
            WorktimeLevelSetting pojo = new WorktimeLevelSetting(n);
            worktimeLevelSettingService.insert(pojo);
        });
        List<WorktimeLevelSetting> l = worktimeLevelSettingService.findByWorktime(2261);		
    }

//    @Transactional
//    @Rollback(true)
//    @Test
    public void testAudit() throws JsonProcessingException {
        DateTime d = new DateTime("2017-09-26").withHourOfDay(0);

        Session session = sessionFactory.getCurrentSession();
        AuditReader reader = AuditReaderFactory.get(session);
        AuditQuery q = reader.createQuery()
                .forRevisionsOfEntity(Worktime.class, false, false)
                .addProjection(AuditEntity.id())
                .add(AuditEntity.id().lt(8607))
                .add(AuditEntity.revisionProperty("REVTSTMP").gt(d.getMillis()))
                .add(AuditEntity.or(
                        AuditEntity.property("assyPackingSop").hasChanged(),
                        AuditEntity.property("testSop").hasChanged()
                ));

        List l = q.getResultList();
        assertEquals(26, l.size());
        HibernateObjectPrinter.print(l);
    }

//    CRUD testing.
//    @Test
//    @Transactional
//    @Rollback(true)
    public void test() throws Exception {
        this.testUpdate();
    }

    public void testUpdate() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Worktime w = (Worktime) session.load(Worktime.class, 17915);
        w.setModelName("TTBB");
        worktimeService.update(w);
        throw new Exception("this is a testing exception");
    }

    private String[] getAllFields() {
        Worktime w = new Worktime();
        Class objClass = w.getClass();

        List<String> list = new ArrayList<>();
        // Get the public methods associated with this class.
        Method[] methods = objClass.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("set") && !name.startsWith("setDefault")) {
                list.add(lowerCaseFirst(name.substring(3)));
            }
        }
        return list.toArray(new String[0]);
    }

    private String lowerCaseFirst(String st) {
        StringBuilder sb = new StringBuilder(st);
        sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
        return sb.toString();
    }

//    @Test
    public void testField() throws Exception {
        Worktime w = worktimeService.findWithFlowRelationAndCobot(8614).get(0);
        assertNotNull(w);

        Flow tf2 = w.getFlowByTestFlowId();
        Flow tf = null;

        if (tf instanceof Flow || tf2 instanceof Flow) {
            String ss = String.format("Different on %s %s -> %s <br/>", "field", tf2 == null ? "null" : ((Flow) tf2).getName(), tf == null ? "null" : ((Flow) tf).getName());
            HibernateObjectPrinter.print(
                    ss
            );
        } else {
            String ss = String.format("Different on %s %s -> %s <br/>", "field", tf2 == null ? "null" : tf2.toString(), tf == null ? "null" : tf.toString());
            HibernateObjectPrinter.print(
                    ss
            );
        }
    }

//    @Test
    @Transactional
    @Rollback(false)
    public void testClone() throws Exception {
        Worktime w = worktimeService.findByPrimaryKey(2261);
        assertNotNull(w);

        String modelName = w.getModelName();

        List<String> modelNames = new ArrayList();

        for (int i = 0; i <= 2; i++) {
            modelNames.add(modelName + "-CLONE-" + i);
        }

        worktimeService.insertSeriesWithMesUpload(modelName, modelNames);
    }

//    @Test
    @Transactional
    @Rollback(false)
    public void deleteClone() throws Exception {
        List<Worktime> l = sessionFactory.getCurrentSession().createQuery("from Worktime w where upper(w.modelName) like '%CLONE%'").list();
        assertEquals(13, l.size());
        l.forEach((w) -> {
            try {
                worktimeService.delete(w);
            } catch (Exception e) {
                System.out.println(w.getModelName() + " delete fail.");
                System.out.println(e);
            }
        });
    }

//    @Test
    @Transactional
    @Rollback(true)
    public void testJava8() throws Exception {
        Worktime w = (Worktime) sessionFactory.getCurrentSession().createQuery("from Worktime w where w.id = 17982").uniqueResult();
        assertNotNull(w);
        worktimeUploadMesService.portParamInit();
        worktimeUploadMesService.delete(w);
    }

//    @Test
    @Transactional
    @Rollback(true)
    public void testLastStatus() {
//        Worktime w = worktimeService.findByPrimaryKey(8066);
//        Worktime rowLastStatus = (Worktime) auditService.findLastStatusBeforeUpdate(Worktime.class, w.getId());
//        System.out.println((int) w.getPartNoAttributeMaintain());
//        System.out.println((int) rowLastStatus.getPartNoAttributeMaintain());
//        System.out.println((int) '5');
//        System.out.println(Objects.equals(w.getPartNoAttributeMaintain(), rowLastStatus.getPartNoAttributeMaintain()));
//        System.out.println(Objects.equals((int) w.getPartNoAttributeMaintain(), (int) rowLastStatus.getPartNoAttributeMaintain()));

    }

//    @Test
    @Transactional
    @Rollback(true)
    public void testTrimModel() throws JsonProcessingException {
        List<Worktime> l = worktimeService.findAll(new PageInfo());
        assertEquals(10, l.size());

        l.stream().map((w) -> {
            w.setModelName(w.getModelName() + " ");
            return w;
        }).forEachOrdered((w) -> {
            this.removeModelNameExtraSpaceCharacter(w);
        });

        HibernateObjectPrinter.print(l);
    }

    private void removeModelNameExtraSpaceCharacter(Worktime w) {
        String modelName = w.getModelName();
        w.setModelName(removeModelNameExtraSpaceCharacter(modelName));
    }

    private String removeModelNameExtraSpaceCharacter(String modelName) {
        return modelName.replaceAll("^\\s+", "").replaceAll("\\s+$", "");
    }

//    @Test
    @Transactional
    @Rollback(true)
    public void testPojoGetSetByName() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Session session = sessionFactory.getCurrentSession();
        Worktime w = (Worktime) session
                .createQuery("from Worktime w order by id desc")
                .setMaxResults(1)
                .uniqueResult();

        assertNotNull(w);

        Object modelName = PropertyUtils.getProperty(w, "modelName");
        assertEquals("HPC7442MB1707-T", modelName);

        Object t1 = PropertyUtils.getProperty(w, "t1");
        assertNotNull(t1);
        assertTrue(new BigDecimal(40).compareTo((BigDecimal) t1) == 0);

        PropertyUtils.setProperty(w, "t1", new BigDecimal(50));
        t1 = PropertyUtils.getProperty(w, "t1");
        assertNotNull(t1);
        assertTrue(new BigDecimal(50).compareTo((BigDecimal) t1) == 0);

    }

//    @Test
    @Transactional
    @Rollback(false)
    public void revisionInit() {
        Session session = sessionFactory.getCurrentSession();
        List<Worktime> l = worktimeService.findAll();
        for (Worktime w : l) {
            w.setCe(0);
            session.update(w);
        }
    }

//    @Test
//    @Transactional
//    @Rollback(false)
    public void testWorktime() {
        List<Worktime> l = worktimeService.findAll();
        Worktime w = l.get(0);
        HibernateObjectPrinter.print(w.getPending().getName());
    }

}
