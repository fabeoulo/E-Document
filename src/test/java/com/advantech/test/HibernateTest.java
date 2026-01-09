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
import com.advantech.model.SuggestionWorktimeHistory;
import com.advantech.model.Worktime;
import com.advantech.model.WorktimeAutouploadSetting;
import com.advantech.model.WorktimeLevelSetting;
import com.advantech.model.WorktimeMaterialPropertyUploadSetting;
import com.advantech.model3.WorktimeReviseddownHistory;
import com.advantech.service.SqlViewService;
import com.advantech.service.SuggestionWorktimeHistoryService;
import com.advantech.service.WorktimeAuditService;
import com.advantech.service.WorktimeAutouploadSettingService;
import com.advantech.service.WorktimeLevelSettingService;
import com.advantech.service.WorktimeMaterialPropertyUploadSettingService;
import com.advantech.service.WorktimeService;
import com.advantech.service.WorktimeUploadMesService;
import com.advantech.service.db3.WorktimeReviseddownHistoryService;
import com.advantech.webservice.port.StandardtimeUploadPort;
import com.advantech.webservice.root.Section;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.Validator;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private SqlViewService sqlViewService;

    @Autowired
    private WorktimeAutouploadSettingService worktimeAutouploadSettingService;

    @Autowired
    private SuggestionWorktimeHistoryService suggestionWorktimeHistoryService;

    @Autowired
    private WorktimeReviseddownHistoryService worktimeReviseddownHistoryService;

    @Before
    public void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
    }

//    @Test
    public void testWorktimeReviseddownHistoryService() {
        List<WorktimeReviseddownHistory> l = worktimeReviseddownHistoryService.findAll();
    }

    @Autowired
    private StandardtimeUploadPort port;
    private final DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd");

//    @Test
//    @Transactional
//    @Rollback(false)
    public void testSqlViewService() {
        List<Map> view = sqlViewService.findSuggestionWorkTime();
        List<Map> viewUpdated = Lists.newArrayList();

        String models = view.stream().map(m -> (String) m.get("modelName"))
                .filter(Objects::nonNull)
                .collect(Collectors.joining(","));

//        models = models + ",TEST1111";
//
        PageInfo tempInfo = new PageInfo();
        tempInfo.setRows(-1);
        tempInfo.setSearchField("modelName");
        tempInfo.setSearchOper("in");
        tempInfo.setSearchString(models);

        Map<String, Worktime> worktimeMap = worktimeService.findWithFullRelation(tempInfo)
                .stream()
                .collect(Collectors.toMap(w -> w.getModelName(), w -> w, (a, b) -> a));

        port.initSettings();
        String today = df.print(new DateTime());
        List<String> errorMessages = Lists.newArrayList();

        view.forEach(m -> {
            String model = (String) m.get("modelName");
            String station = convertStation((String) m.get("station"));
            String process = convertProcess((String) m.get("station"));
            BigDecimal wt = (BigDecimal) m.get("standardTime");
            BigDecimal suggestWt = (BigDecimal) m.get("suggestSt");

//            model = "TEST1111";
//            wt = BigDecimal.valueOf(0);
//
            Worktime worktime = worktimeMap.get(model);
            if (worktime != null) {
                try {
                    Field field = worktime.getClass().getDeclaredField(station);
                    field.setAccessible(true);
                    Class type = field.getType();
                    BigDecimal v = (BigDecimal) field.get(worktime);

                    if (type.equals(BigDecimal.class) && v.compareTo(wt) == 0) {
//                        field.set(worktime, suggestWt);
//                        worktimeService.updateWithoutMesUpload(worktime);

//                        port.update(worktime);
//                        HibernateObjectPrinter.print("Upload standardtime of model " + worktime.getModelName() + " on station " + station);
////                        log.info("Upload standardtime of model " + worktime.getModelName() + " on station " + station);
                        BigDecimal percent = wt.compareTo(BigDecimal.ZERO) > 0
                                ? wt.subtract(suggestWt).abs().multiply(BigDecimal.valueOf(100))
                                        .divide(wt, 2, RoundingMode.HALF_UP)
                                : BigDecimal.ZERO;

                        m.put("datetime", today);
                        m.put("process", process);
                        m.put("workCenter", worktime.getWorkCenter().getName());
                        m.put("revisedDownPercent", percent);
                        viewUpdated.add(m);
                    }
                } catch (Exception e) {
                    String errorMessage = worktime.getModelName() + " on station " + station + " upload fail: " + e.getMessage();
                    errorMessages.add(errorMessage);
//                    log.error(errorMessage);
                    HibernateObjectPrinter.print(errorMessage);
                }
            }
        });
    }

    private String convertStation(String station) {
        String fieldName = "";

        if (station != null) {
            switch (station.toUpperCase()) {
                case "T1":
                    fieldName = "t1";
                    break;
                case "T2":
                    fieldName = "t2";
                    break;
                case "T3":
                    fieldName = "t3";
                    break;
                case "SL":
                    fieldName = "seal";
                    break;
                case "SL1":
                    fieldName = "seal1";
                    break;
                case "ASSY1":
                    fieldName = "bondedSealingFrame";
                    break;
                case "ASSY2":
                    fieldName = "assy2";
                    break;
                case "OB":
                    fieldName = "opticalBonding";
                    break;
                case "OB1":
                    fieldName = "opticalBonding1";
                    break;
                case "OB2":
                    fieldName = "opticalBonding2";
                    break;
                case "ASSY":
                    fieldName = "assy";
                    break;
                case "PACKAGE":
                    fieldName = "packing";
                    break;
                case "PRE_ASSY":
                    fieldName = "pi";
                    break;
                default:
            }
        }
        return fieldName;
    }

    private String convertProcess(String station) {
        String flowName = "";

        if (station != null) {
            switch (station.toUpperCase()) {
                case "T1":
                case "T2":
                case "T3":
                    flowName = Section.TEST.getCode();
                    break;
                case "SL":
                case "SL1":
                case "ASSY1":
                case "ASSY2":
                case "OB":
                case "OB1":
                case "OB2":
                case "ASSY":
                    flowName = Section.BAB.getCode();
                    break;
                case "PACKAGE":
                    flowName = Section.PACKAGE.getCode();
                    break;
                case "PRE_ASSY":
                    flowName = Section.PREASSY.getCode();
                    break;
                default:
            }
        }
        return flowName;
    }

//    @Test
    public void testSuggestionWorktimeHistoryService() {
        List<SuggestionWorktimeHistory> l = suggestionWorktimeHistoryService.findAll();
    }

//    @Test
    public void testWorktimeAutouploadSettingService() {
        PageInfo info = new PageInfo();
        info.setRows(-1);
        info.setSearchField("stationId");
        info.setSearchOper("ne");
        info.setSearchString(null);
        List<WorktimeAutouploadSetting> l = worktimeAutouploadSettingService.findAll(info);

        List<JsonObject> l3 = l.stream()
                .map(i
                        -> Json.createObjectBuilder()
                        .add("id", i.getId())
                        .add("name", i.getColumnName())
                        .build()
                )
                .sorted(Comparator.comparing(
                        js -> js.getString("name"),
                        String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
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
