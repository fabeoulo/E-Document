/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.service.db2;

import com.advantech.helper.HibernateObjectPrinter;
import com.advantech.model.db2.*;
import com.advantech.test.ExcelTest;
import com.advantech.webservice.download.FlowM4fDownload;
import com.advantech.webservice.download.MaterialPropertyValueM4fDownload;
import com.advantech.webservice.download.ModelResponsorM4fDownload;
import com.advantech.webservice.download.MtdTestIntegrityM4fDownload;
import com.advantech.webservice.download.StandardWorkTimeM4fDownload;
import com.google.common.base.Preconditions;
import static com.google.common.collect.Lists.newArrayList;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
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
 * @author Justin.Yeh
 */
@WebAppConfiguration
@ContextConfiguration(locations = {
    "classpath:servlet-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class WorktimeDownloadMesM4fServiceTest {

    @Autowired
    private WorktimeM4fService instance;

    private List<WorktimeM4f> l = newArrayList();

    @Autowired
    private FlowM4fDownload flowM4fDownload;

    @Autowired
    private ModelResponsorM4fDownload modelResponsorM4fDownload;

    @Autowired
    private MtdTestIntegrityM4fDownload mtdTestIntegrityM4fDownload;

    @Autowired
    private MaterialPropertyValueM4fDownload materialPropertyValueM4fDownload;

    @Autowired
    private WorktimeDownloadMesM4fService worktimeDownloadMesM4fService;

    @Autowired
    private StandardWorkTimeM4fDownload standardWorkTimeM4fDownload;

    @PostConstruct
    private void init() {
        List<String> modelNames = newArrayList("WISE-3620ILS-22A10");//"DMS-SE24-00A3E","UBCDS31CD1801-T"//"DLV8312-1RM510WFC0","DMS-SE24-00A1E","DMS-SA65-03A1E"
        Preconditions.checkState(!modelNames.isEmpty(), "No model names. Finish.");

        modelNames.forEach(m -> {
            WorktimeM4f wm4 = new WorktimeM4f();
            wm4.setModelName(m);
            l.add(wm4);
        });
    }

    public static List<WorktimeM4f> getExcelModels() {
        List<String> modelNames = ExcelTest.getExcelModels();

        List<WorktimeM4f> l = newArrayList();
        modelNames.forEach(m -> {
            WorktimeM4f wm4 = new WorktimeM4f();
            wm4.setModelName(m);
            l.add(wm4);
        });

        return l;
    }

//    @Test
//    @Transactional
//    @Rollback(false)
    public void testWorktimeDownloadMesM4fService() throws Exception {
        System.out.println("testWorktimeDownloadMesM4fService");

//        l = getExcelModels();
//        worktimeDownloadMesM4fService.portParamInit();
//        worktimeDownloadMesM4fService.insertMesDL(l);
//        HibernateObjectPrinter.print(l);
//
//        List<String> modelNames = ExcelTest.getExcelModels();
        List<String> modelNames = Arrays.asList("USM-D64-E80");
        worktimeDownloadMesM4fService.saveOrUpdateByModels(modelNames);

    }

//    @Test
    public void testSaveOrUpdate() {
        List<String> modelNames = Arrays.asList("DLV72122010-T", "DMS-AF55SFM-S5A1", "EBC-AF52T8F-U0A1");
        try {
            this.saveOrUpdateByModels(modelNames);
        } catch (Exception e) {
        }
    }

    @Autowired
    private M9ieWorktimeViewService m9ieWorktimeViewService;

    @Autowired
    private WorktimeM4fService worktimeM4fService;

    @Autowired
    private BusinessGroupM4fService businessGroupM4fService;

    private void saveOrUpdateByModels(List<String> modelNames) throws Exception {
        // SQL limit to 2000 params(modelNames).
        List<M9ieWorktimeView> views;
        if (!modelNames.isEmpty()) {
            String[] arr = modelNames.stream().limit(2000).toArray(String[]::new);
            views = m9ieWorktimeViewService.findByModelNames(arr);
        } else {
            views = m9ieWorktimeViewService.findAll();
        }
        Map<String, List> listMap = filterByViewAndSetBg(views);

        List<WorktimeM4f> wtIn = listMap.get("wtIn");
//        List<WorktimeM4f> wtIn = worktimeM4fService.findByModelNames("DLV72122010-T", "DMS-AF55SFM-S5A1", "EBC-AF52T8F-U0A1");
//
        worktimeM4fService.mergeWithoutUpload(wtIn);
        return;
    }

    private Map<String, List> filterByViewAndSetBg(List<M9ieWorktimeView> views) {

        Map<String, M9ieWorktimeView> m9Map = views.stream().collect(Collectors.toMap(v -> v.getModelName(), v -> v, (a, b) -> b));
        Map<String, WorktimeM4f> wtMap = worktimeM4fService.findAll().stream().collect(Collectors.toMap(wt -> wt.getModelName(), wt -> wt));
        Map<String, BusinessGroupM4f> buMap = businessGroupM4fService.findAll().stream().collect(Collectors.toMap(bu -> bu.getWorkCenter(), bu -> bu));

//        log.info("M9ieWorktimeView size : " + m9Map.size() + " WorktimeM4f size : " + wtMap.size());
        HibernateObjectPrinter.print("M9ieWorktimeView size : " + m9Map.size() + " WorktimeM4f size : " + wtMap.size());

        List<WorktimeM4f> wtIn = new ArrayList<>();
        List<WorktimeM4f> wtNew = new ArrayList<>();
        m9Map.entrySet().stream().forEach(m9En -> {

            String modelName = m9En.getKey();
            M9ieWorktimeView m9wt = m9En.getValue();

            WorktimeM4f w;
            if (wtMap.containsKey(modelName)) {
                w = wtMap.get(modelName);
                wtIn.add(w);
            } else {
                w = new WorktimeM4f();
                w.setModelName(modelName);
                wtNew.add(w);
            }

            String wc = m9wt.getWorkCenter();
            w.setWorkCenter(wc);
            w.setBusinessGroup(buMap.getOrDefault(wc, null));
        });

        Map<String, List> resultMap = new HashMap<>();
        resultMap.put("wtIn", wtIn);
        resultMap.put("wtNew", wtNew);
        return resultMap;
    }

//    @Test // OK
    public void testStandardWorkTimeM4fDownload() {
        System.out.println("testStandardWorkTimeM4fDownload");

//        getExcelModels();
        l = instance.findByModelNames("DLV72122010-T", "DMS-AF55SFM-S5A1", "EBC-AF52T8F-U0A1");

        standardWorkTimeM4fDownload.initOptions();
        try {
            for (WorktimeM4f wm4 : l) {
                HibernateObjectPrinter.print("Processing: " + wm4.getModelName() + " in thread: " + Thread.currentThread().getName());

                standardWorkTimeM4fDownload.download(wm4);
//                setNotNullFieldDefault(wm4);
            }
        } catch (Exception e) {
            HibernateObjectPrinter.print(e.getMessage());
        }
        HibernateObjectPrinter.print(l);
    }

//    @Test // OK
    public void testMaterialPropertyValueM4fDownload() {
        System.out.println("testMaterialPropertyValueM4fDownload");
//        getExcelModels();

        materialPropertyValueM4fDownload.initOptions();
        try {
            for (WorktimeM4f wm4 : l) {
                HibernateObjectPrinter.print("Processing: " + wm4.getModelName() + " in thread: " + Thread.currentThread().getName());

                materialPropertyValueM4fDownload.download(wm4);
//                setNotNullFieldDefault(wm4);
            }
        } catch (Exception e) {
            HibernateObjectPrinter.print(e.getMessage());
        }
        HibernateObjectPrinter.print(l);
    }

//    @Test // OK
    public void testMtdTestIntegrityM4fDownload() {
        System.out.println("testMtdTestIntegrityM4fDownload");
//        getExcelModels();

        try {
            for (WorktimeM4f wm4 : l) {
                HibernateObjectPrinter.print("Processing: " + wm4.getModelName() + " in thread: " + Thread.currentThread().getName());

                mtdTestIntegrityM4fDownload.download(wm4);
//                setNotNullFieldDefault(wm4);
            }
        } catch (Exception e) {
            HibernateObjectPrinter.print(e.getMessage());
        }
        HibernateObjectPrinter.print(l);
    }

//    @Test // OK
    public void testModelResponsorM4fDownload() {
        System.out.println("testModelResponsorM4fDownload");//DMS-SA65-T2C1E 2SPE
//        getExcelModels();

        modelResponsorM4fDownload.initOptions();
        try {
            for (WorktimeM4f wm4 : l) {
                HibernateObjectPrinter.print("Processing: " + wm4.getModelName() + " in thread: " + Thread.currentThread().getName());

                modelResponsorM4fDownload.download(wm4);
//                setNotNullFieldDefault(wm4);
            }
        } catch (Exception e) {
            HibernateObjectPrinter.print(e.getMessage());
        }
        HibernateObjectPrinter.print(l);
    }

//    @Test // OK
    public void testFlowM4fDownload() {
        System.out.println("testFlowM4fDownload");
//        getExcelModels();

        flowM4fDownload.initOptions();
        try {
            for (WorktimeM4f wm4 : l) {
                HibernateObjectPrinter.print("Processing: " + wm4.getModelName() + " in thread: " + Thread.currentThread().getName());

                flowM4fDownload.download(wm4);
//                setNotNullFieldDefault(wm4);
            }
        } catch (Exception e) {
            HibernateObjectPrinter.print(e.getMessage());
        }
        HibernateObjectPrinter.print(l);
    }

//    @Test
//    @Transactional
//    @Rollback(false)
    public void testInsertByModels() {
        System.out.println("testInsertByModels");
//        getExcelModels();

//        flowM4fDownload.initOptions();
//        modelResponsorM4fDownload.initOptions();
        materialPropertyValueM4fDownload.initOptions();
//        standardWorkTimeM4fDownload.initOptions();
        try {
            for (WorktimeM4f wm4 : l) {
//                WorktimeM4f wm4 = (WorktimeM4f) wt;
                HibernateObjectPrinter.print("Processing: " + wm4.getModelName() + " in thread: " + Thread.currentThread().getName());
//
//                modelResponsorM4fDownload.download(wm4);
//                flowM4fDownload.download(wm4);
//                mtdTestIntegrityM4fDownload.download(wm4);
                materialPropertyValueM4fDownload.download(wm4);
//                standardWorkTimeM4fDownload.download(wm4);

//                setNotNullFieldDefault(wm4);
            }
        } catch (Exception e) {
            HibernateObjectPrinter.print(e.getMessage());

        }

        HibernateObjectPrinter.print(l);
//        instance.insertByExcelModels(l);
    }

    private void setNotNullFieldDefault(IWorktimeForWebService wt) throws Exception {
        Class clz = wt.getClass();
        Method[] methods = clz.getDeclaredMethods(); // includes super class method, i.e. getter may return diff type.

        for (Method method : methods) {
            if (method.isAnnotationPresent(NotNull.class)) {
                if (isGetter(method)) {
                    String fieldName = getFieldNameFromGetter(method);

                    Field field = clz.getDeclaredField(fieldName);
                    Preconditions.checkNotNull(field, "Cannot find field: " + fieldName);
                    field.setAccessible(true);
                    if (field.get(wt) == null) {
                        Object valueToSet = getDefaultByType(method.getReturnType(), fieldName);
                        Preconditions.checkNotNull(valueToSet, "Fail to set null value for field: " + fieldName);
                        field.set(wt, valueToSet);
                    }
                }
            }
        }
    }

    private boolean isGetter(Method method) {
        if (method.getName().startsWith("get") && method.getParameterCount() == 0 && !method.getReturnType().equals(void.class)) {
            return true;
        } else if (method.getName().startsWith("is") && method.getParameterCount() == 0 && method.getReturnType().equals(boolean.class)) {
            return true;
        }
        return false;
    }

    private String getFieldNameFromGetter(Method method) {
        String methodName = method.getName();
        if (methodName.startsWith("get")) {
            return Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
        }
        if (methodName.startsWith("is")) {
            return Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
        }
        return null;
    }

    private Object getDefaultByType(Class<?> type, String fieldName) {
        if (type.equals(UserM4f.class) || type.equals(IUserM9.class)) {
            switch (fieldName) {
                case "userBySpeOwnerId":
                    return new UserM4f(78);
                case "userByQcOwnerId":
                    return new UserM4f(2);
                default:
                    return new UserM4f(40);
            }
        } else if (type.equals(FloorM4f.class)) {
            return new FloorM4f(6, "4F");
        } else if (type.equals(PendingM4f.class)) {
            return new PendingM4f(3);
        } else if (type.equals(TypeM4f.class)) {
            return new TypeM4f(6, "MP");
        } else if (type.equals(BusinessGroupM4f.class)) {
            return new BusinessGroupM4f(5);
        } else if (type.equals(BigDecimal.class)) {
            return BigDecimal.ZERO;
        } else if (type.equals(String.class)) {
            switch (fieldName) {
                case "biPower":
                    return "<300W";
                default:
                    return "";
            }
        } else if (type.equals(Character.class)) {
            if (fieldName.equals("partLink") || fieldName.equals("labelYN")) {
                return 'Y';
            } else {
                return ' ';
            }
        } else if (type.equals(Integer.class)) {
            return 0;
        } else {
            System.out.println("Unsupported type: " + type.getName());
            return null;
        }
    }
}
