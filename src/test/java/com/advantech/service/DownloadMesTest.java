/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.service;

import com.advantech.model2.TypeM4f;
import com.advantech.model2.IWorktimeForWebService;
import com.advantech.model2.IUserM9;
import com.advantech.model2.BusinessGroupM4f;
import com.advantech.model2.FloorM4f;
import com.advantech.model2.UserM4f;
import com.advantech.helper.CustomPasswordEncoder;
import com.advantech.helper.HibernateObjectPrinter;
import com.advantech.helper.SpringExpressionUtils;
import com.advantech.model.Floor;
import com.advantech.model.Unit;
import com.advantech.model.User;
import com.advantech.model.UserProfile;
import com.advantech.model.Worktime;
import com.advantech.model.*;
import com.advantech.security.State;
import com.advantech.test.ExcelTest;
import com.advantech.webservice.Factory;
import com.advantech.webservice.port.MaterialFlowQueryPort;
import com.advantech.webservice.port.MaterialPropertyValueQueryPort;
import com.advantech.webservice.port.ModelResponsorQueryPort;
import com.advantech.webservice.port.MtdTestIntegrityQueryPort;
import com.advantech.webservice.port.StandardWorkTimeQueryPort;
import com.advantech.webservice.root.DeptIdM9;
import com.advantech.webservice.root.Section;
import com.advantech.webservice.unmarshallclass.*;
import com.google.common.base.Preconditions;
import static com.google.common.collect.Lists.newArrayList;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import org.apache.commons.beanutils.PropertyUtils;
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
public class DownloadMesTest {

    @Autowired
    private WorktimeService instance;

    private List<Worktime> l = newArrayList();

//=============================
    @Autowired
    private MtdTestIntegrityQueryPort mtdTestIntegrityQueryPort;
//=============================
//
//    @Autowired
//    private PreAssyM4fService preAssyService;
//    @Autowired
//    private FlowM4fService flowService;
//
//    private Map<String, PreAssyM4f> preAssyOptions;
//    private Map<String, FlowM4f> flowOptions;
//
//    @Autowired
//    private MaterialFlowQueryPort materialFlowQueryPort;
//=============================
    @Autowired
    private WorktimeMaterialPropertyDownloadSettingService propSettingService;

    private List<WorktimeMaterialPropertyDownloadSetting> settings = new ArrayList();

    @Autowired
    private SpringExpressionUtils expressionUtils;

    @Autowired
    private PendingService pendingService;
    @Autowired
    private CartonLabelService cartonLabelService;
    @Autowired
    private OutLabelService outLabelService;

    private Map<String, Pending> pendingOptions;
    private Map<String, OutLabel> outLabelOptions;
    private Map<String, CartonLabel> cartonLabelOptions;

    @Autowired
    private MaterialPropertyValueQueryPort materialPropertyValueQueryPort;
////=============================
//    @Autowired
//    private UserM4fService userService;
//    @Autowired
//    private UserProfileM4fService userProfileService;
//    @Autowired
//    private UnitM4fService unitService;
//    @Autowired
//    private FloorM4fService floorService;
//
//    @Autowired
//    private UserService userService1;
//    @Autowired
//    private UserProfileService userProfileService1;
//    @Autowired
//    private UnitService unitService1;
//    @Autowired
//    private FloorService floorService1;
//
//    private Map<String, IUserM9> userOptions;
//    private Map<String, UnitM4f> unitOptions;
//    private Map<String, IUserM9> userOptions1;
//    private Map<String, Unit> unitOptions1;
//
//    @Autowired
//    private ModelResponsorQueryPort modelResponsorQueryPort;
//=============================

    @Autowired
    private StandardWorkTimeQueryPort worktimeQueryPort;

    @Autowired
    private WorktimeAutodownloadSettingService worktimeAutodownloadSettingService;

    private List<WorktimeAutodownloadSetting> autodownloadSettings = new ArrayList();
//=============================    

    @PostConstruct
    private void init() {
        List<String> modelNames = newArrayList("DMS-SA55-01A2E");
//        List<String> modelNames = newArrayList("EKI-9516-P0IDH10E-TEST","EKI-7706G-2FI-AU","EKI-7706G-2F-AU","PDCW2402301-T","PDCW2402002-T");
        Preconditions.checkState(!modelNames.isEmpty(), "No model names. Finish.");

        modelNames.forEach(m -> {
            Worktime wm4 = new Worktime();
            wm4.setModelName(m);
            l.add(wm4);
        });
    }

    private void getExcelModels() {
        List<String> modelNames = ExcelTest.getExcelModels();
        l.clear();

        modelNames.forEach(m -> {
            Worktime wm4 = new Worktime();
            wm4.setModelName(m);
            l.add(wm4);
        });
    }

    private void initOptions() throws Exception {
//        userOptions = toSelectOptions(userService.findAll());
//        unitOptions = toSelectOptions(unitService.findAll());
//        userOptions1 = toSelectOptions(userService1.findAll());
//        unitOptions1 = toSelectOptions(unitService1.findAll());
//
//        preAssyOptions = toSelectOptions(preAssyService.findAll());
//        flowOptions = toSelectOptions(flowService.findAll());
//
//        pendingOptions = toSelectOptions(pendingService.findAll());
//        outLabelOptions = toSelectOptions(outLabelService.findAll());
//        cartonLabelOptions = toSelectOptions(cartonLabelService.findAll());
//
        settings = propSettingService.findAll();
//        expressionUtils.setVariable("pendingMap", pendingOptions);
//        expressionUtils.setVariable("outLabelMap", outLabelOptions);
//        expressionUtils.setVariable("cartonLabelMap", cartonLabelOptions);
//
//        autodownloadSettings = worktimeAutodownloadSettingService.findAll();
    }

//    @Test
//    @Transactional
//    @Rollback(true)
    public void testDlMatWithUpdate() throws Exception {
        System.out.println("testDlMatWithUpdate");

        l = instance.findAll();
//        l = instance.findByModelNames("DMS-SA55-01A2E", "EKI-9516-P0IDH10E-TEST", "UNO-4683-D34E");

//        initOptions();
        for (Worktime wm4 : l) {
            HibernateObjectPrinter.print("Processing: " + wm4.getModelName() + " in thread: " + Thread.currentThread().getName());
            try {
                dlMtdTest(wm4);

//                setNotNullFieldDefault(wm4);
//
                instance.update(wm4);
                HibernateObjectPrinter.print(wm4);
            } catch (Exception e) {
//            logger.error(e.getMessage(), e);
                HibernateObjectPrinter.print(e.getMessage());
                throw e;
            }
        }
    }

//    @Test
//    @Transactional
//    @Rollback(true)
    public void testInsertByExcelModel() throws Exception {
        System.out.println("testInsertByExcelModel");

//        getExcelModels();
//
        initOptions();
        for (Worktime wm4 : l) {
//                Worktime wm4 = (Worktime) wt;
//
            HibernateObjectPrinter.print("Processing: " + wm4.getModelName() + " in thread: " + Thread.currentThread().getName());
            try {
//                dlOwner(wm4);
//                dlFlow(wm4);
                dlMat(wm4);
//                dlMtdTest(wm4);
//                dlWt(wm4);

//                setNotNullFieldDefault(wm4);
//
            } catch (Exception e) {
//            logger.error(e.getMessage(), e);
                HibernateObjectPrinter.print(e.getMessage());
                throw e;
            }
        }

        HibernateObjectPrinter.print(l);
//        instance.insertByMesModels(l);
    }

    private Worktime dlWt(Worktime wt) throws Exception {
        List<StandardWorkTime> standardWorktimes = worktimeQueryPort.queryM(wt.getModelName(), Factory.TWM3);
        Map<String, String> errorFields = new HashMap();

        wt.setAssyStation(1);
        wt.setPackingStation(1);
        autodownloadSettings.forEach((setting) -> {
            try {
                StandardWorkTime worktimeOnMes = standardWorktimes.stream()
                        .filter(p -> (Objects.equals(p.getSTATIONID(), setting.getStationId()) || (p.getSTATIONID() == -1 && setting.getStationId() == null))
                        && (p.getLINEID() == setting.getLineId())
                        && Objects.equals(p.getUNITNO(), setting.getColumnUnit())
                        && Objects.equals(p.getITEMNO(), wt.getModelName()))
                        .findFirst().orElse(null);

                if (worktimeOnMes != null) {
                    Object totalctVal = expressionUtils.getValueFromFormula(worktimeOnMes, setting.getFormulaTotalct());
                    String dlColumn = (String) expressionUtils.getValueFromFormula(wt, setting.getFormulaColumn());
                    expressionUtils.setValueFromFormula(wt, dlColumn, totalctVal);

                    String columnUnit = setting.getColumnUnit();
                    if ("B".equals(columnUnit) && setting.getStationId() != null) {
                        wt.setAssyStation(worktimeOnMes.getOPCNT());
                    } else if ("P".equals(columnUnit) && setting.getStationId() != null) {
                        wt.setPackingStation(worktimeOnMes.getOPCNT());
                    }
                }

            } catch (Exception e) {
                errorFields.put(setting.getColumnName(), e.getMessage());
            }
        });

        if (!errorFields.isEmpty()) {
            throw new Exception(wt.getModelName() + " 工時從MES讀取失敗: " + errorFields.toString());
        }

        return wt;
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

    private static String getFieldNameFromGetter(Method method) {
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
        } else if (type.equals(Pending.class)) {
            return new Pending(3);
        } else if (type.equals(TypeM4f.class)) {
            return new TypeM4f(6, "MP");
        } else if (type.equals(BusinessGroupM4f.class)) {
            return new BusinessGroupM4f(5);
        } else if (type.equals(BigDecimal.class)) {
            return BigDecimal.ZERO;
        } else if (type.equals(String.class)) {
            return "";
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

//    private Worktime dlOwner(Worktime wt) throws Exception {
//        List<ModelResponsor> mesOwners = modelResponsorQueryPort.queryM(wt, Factory.TWM3);
//        Map<String, String> errorFields = new HashMap();
//
//        CustomPasswordEncoder encoder = new CustomPasswordEncoder();
//
//        UserProfile userProfile1 = userProfileService1.findByType("USER");
//        Set<UserProfile> userProfiles1 = new HashSet<>(Arrays.asList(userProfile1));
//        Floor floor1 = floorService1.findByPrimaryKey(6);
//
//        UserProfileM4f userProfile = userProfileService.findByType("USER");
//        Set<UserProfileM4f> userProfiles = new HashSet<>(Arrays.asList(userProfile));
//        FloorM4f floor = floorService.findByPrimaryKey(6);
//
//        for (DeptIdM9 deptIdM9 : DeptIdM9.values()) {
//            try {
//                ModelResponsor mr = mesOwners.stream().filter(mo -> mo.getDeptId() == deptIdM9.getCode()).findFirst().orElse(null);
//                if (mr == null) {
//                    continue;
//                }
//
//                String jobNo = mr.getUserNo();
//                String email = mr.getEmail();
//                if (isNullOrEmpty(jobNo) || isNullOrEmpty(email)) {
//                    continue;
//                }
//                String username = email.split("@")[0];
//                String password = encoder.encode(jobNo);
//
//                User u1 = (User) userOptions1.get(jobNo);
//                if (u1 == null) {
//                    String state = State.INACTIVE.getState();
//                    Unit unit = (Unit) unitOptions1.get(deptIdM9.toString());
//                    u1 = new User(0, floor1, unit, email, jobNo, password, 0, username, state, userProfiles1, null, null, null, null);
//                    userService1.insert(u1);
//                    userOptions1.put(jobNo, u1);
//                }
//
//                UserM4f u = (UserM4f) userOptions.get(jobNo);
//                if (u == null) {
//                    String state = State.ACTIVE.getState();
//
//                    UnitM4f unit = (UnitM4f) unitOptions.get(deptIdM9.toString());
//                    u = new UserM4f(0, floor, unit, email, jobNo, password, 0, username, state, userProfiles, null, null, null, null);
//                    userService.insert(u);
//                    userOptions.put(jobNo, u);
//                }
//                switch (deptIdM9) {
//                    case SPE:
//                        wt.setUserBySpeOwnerId(u);
//                        break;
//                    case EE:
//                        wt.setUserByEeOwnerId(u);
//                        break;
//                    case QC:
//                        wt.setUserByQcOwnerId(u);
//                        break;
//                    case MPM:
//                        wt.setUserByMpmOwnerId(u);
//                        break;
//                    default:
//                        break;
//                }
//            } catch (Exception e) {
//                errorFields.put(deptIdM9 + "_Owner", e.getMessage());
//            }
//        }
//        if (!errorFields.isEmpty()) {
//            throw new Exception(wt.getModelName() + " 機種負責人從MES讀取失敗: " + errorFields.toString());
//        }
//
//        return wt;
//    }
////
//    private Worktime dlFlow(Worktime wt) throws Exception {
//        List<MaterialFlow> mesFlows = materialFlowQueryPort.queryM(wt, Factory.TWM3);
//        Map<String, String> errorFields = new HashMap();
//
//        for (Section section : Section.values()) {
//            try {
//                MaterialFlow mf = mesFlows.stream().filter(materialFlow -> materialFlow.getUnitNo().equals(section.getCode())).findFirst().orElse(null);
//                if (mf == null) {
//                    continue;
//                }
//
//                String flowName = mf.getFlowRuleName();
//                if (isNullOrEmpty(flowName)) {
//                    continue;
//                }
//
//                switch (section) {
//                    case PREASSY:
//                        PreAssyM4f preAssy = preAssyOptions.get(flowName);
//                        if (preAssy == null) {
//                            preAssy = new PreAssyM4f();
//                            preAssy.setName(flowName);
//                            preAssyService.insert(preAssy);
//                            preAssyOptions.put(flowName, preAssy);
//                        }
//                        wt.setPreAssy(preAssy);
//                        break;
//                    case BAB:
//                        FlowM4f babFlow = flowOptions.get(flowName);
//                        if (babFlow == null) {
//                            babFlow = new FlowM4f();
//                            babFlow.setName(flowName);
//                            babFlow.setFlowGroup(new FlowGroupM4f(1));
//                            flowService.insert(babFlow);
//                            flowOptions.put(flowName, babFlow);
//                        }
//                        wt.setFlowByBabFlowId(babFlow);
//                        break;
//                    case TEST:
//                        FlowM4f testFlow = flowOptions.get(flowName);
//                        if (testFlow == null) {
//                            testFlow = new FlowM4f();
//                            testFlow.setName(flowName);
//                            testFlow.setFlowGroup(new FlowGroupM4f(3));
//                            flowService.insert(testFlow);
//                            flowOptions.put(flowName, testFlow);
//                        }
//                        wt.setFlowByTestFlowId(testFlow);
//                        break;
//                    case PACKAGE:
//                        FlowM4f pkgFlow = flowOptions.get(flowName);
//                        if (pkgFlow == null) {
//                            pkgFlow = new FlowM4f();
//                            pkgFlow.setName(flowName);
//                            pkgFlow.setFlowGroup(new FlowGroupM4f(2));
//                            flowService.insert(pkgFlow);
//                            flowOptions.put(flowName, pkgFlow);
//                        }
//                        wt.setFlowByPackingFlowId(pkgFlow);
//                        break;
//                    default:
//                        break;
//                }
//            } catch (Exception e) {
//                errorFields.put(section + "_Flow", e.getMessage());
//            }
//        }
//
//        if (!errorFields.isEmpty()) {
//            throw new Exception(wt.getModelName() + " 徒程從MES讀取失敗: " + errorFields.toString());
//        }
//
//        if (wt.getFlowByBabFlowId() != null && wt.getFlowByTestFlowId() != null) {
//            int babFlowId = wt.getFlowByBabFlowId().getId();
//            int testFlowId = wt.getFlowByTestFlowId().getId();
//
//            List<FlowM4f> testFlows = flowService.findByParent(babFlowId);
//            if (testFlows.stream().noneMatch(f -> f.getId() == testFlowId)) {
//                List<Integer> addSubIds = new ArrayList();
//                addSubIds.add(testFlowId);
//                flowService.addSub(babFlowId, addSubIds);
//            }
//        }
//
//        return wt;
//    }
//
//    @Test
//    @Transactional
//    @Rollback(false)
    public void dlMatTest() throws Exception {

        List<Worktime> wts = instance.findByModelNames("EKI-9516-P0IDH10E-TEST");
//        List<Worktime> wts = instance.findAll();
        wts.forEach(wt -> {
            try {
                dlNewMatM3(wt);

                HibernateObjectPrinter.print(wt.getModelName() + "," + wt.getKeypartBlockFlag() + "," + wt.getKeypartBlockFlag());
//                instance.update(wt);
            } catch (Exception ex) {
                HibernateObjectPrinter.print(ex);
            }
        });
    }

    private void dlNewMatM3(Worktime wt) throws Exception {
//        WorktimeMaterialPropertyDownloadSetting dlSetting = new WorktimeMaterialPropertyDownloadSetting();
//        dlSetting.setMatPropNo("KPA");
//        dlSetting.setDlColumn("keypartBlockFlag");
//        dlSetting.setDlFormula("\"Y\".equals(value) ? \"Y\" : \"N\"");

//        WorktimeMaterialPropertyDownloadSetting dlSetting2 = new WorktimeMaterialPropertyDownloadSetting();
//        dlSetting2.setMatPropNo("KPA");
//        dlSetting2.setDlColumn("keypartBlockFlag");
//        dlSetting2.setDlFormula("value.length() > 1 ? value.substring(0, 1) :value.length() ==0 ? \"N\" : value");
//        List<WorktimeMaterialPropertyDownloadSetting> settings = newArrayList(dlSetting2);
//
        initOptions();
        settings = settings.stream().filter(s -> s.getMatPropNo().equals("KPA")).collect(toList());

        List<MaterialPropertyValue> remotePropSettings = materialPropertyValueQueryPort.queryM(wt, Factory.TWM3);
        Map<String, String> errorFields = new HashMap();
        settings.forEach((setting) -> {
            try {
                MaterialPropertyValue mp = remotePropSettings.stream()
                        .filter(r -> Objects.equals(wt.getModelName(), r.getItemNo()) && Objects.equals(setting.getMatPropNo(), r.getMatPropertyNo()))
                        .findFirst().orElse(null);

                if (mp == null) {
                    mp = new MaterialPropertyValue();
                    mp.setValue("");
                    mp.setAffPropertyValue("");
                } else {
                    mp.setValue(notNull(mp.getValue()));
                    mp.setAffPropertyValue(notNull(mp.getAffPropertyValue()));
                }

                String dlFormula = setting.getDlFormula();
                String dlColumn = setting.getDlColumn();
                String dlAffFormula = setting.getDlAffFormula();
                String dlAffColumn = setting.getDlAffColumn();
                String dlFormula2 = setting.getDlFormula2();
                String dlColumn2 = setting.getDlColumn2();

                Object mesConvertVal = expressionUtils.getValueFromFormula(mp, dlFormula);
                Object mesConvertValAff = expressionUtils.getValueFromFormula(mp, dlAffFormula);
                Object mesConvertVal2 = expressionUtils.getValueFromFormula(mp, dlFormula2);

                expressionUtils.setValueFromFormula(wt, dlColumn, mesConvertVal);
                expressionUtils.setValueFromFormula(wt, dlAffColumn, mesConvertValAff);
                expressionUtils.setValueFromFormula(wt, dlColumn2, mesConvertVal2);

                Object wtVal = expressionUtils.getValueFromFormula(wt, dlColumn);
                if (wtVal != null && !wtVal.toString().equals(mesConvertVal)) {
                    HibernateObjectPrinter.print(wt.getModelName() + "," + wtVal.toString() + "," + mesConvertVal.toString());
                }

            } catch (Exception e) {
                errorFields.put(setting.getMatPropNo() + ":==", e.getMessage());
            }
        });
        if (!errorFields.isEmpty()) {
            throw new Exception(wt.getModelName() + " 屬性從MES讀取失敗: " + errorFields.toString());
        }

    }

    private void dlMat(Worktime wt) throws Exception {
        List<MaterialPropertyValue> remotePropSettings = materialPropertyValueQueryPort.queryM(wt, Factory.TWM3);
        Map<String, String> errorFields = new HashMap();

//        settings = settings.stream().filter(s -> s.getMatPropNo().equals("IW")).collect(toList());
        settings.forEach((setting) -> {
            try {
                MaterialPropertyValue mp = remotePropSettings.stream()
                        .filter(r -> Objects.equals(wt.getModelName(), r.getItemNo()) && Objects.equals(setting.getMatPropNo(), r.getMatPropertyNo()))
                        .findFirst().orElse(null);

                if (mp == null) {
                    mp = new MaterialPropertyValue();
                    mp.setValue("");
                    mp.setAffPropertyValue("");
                } else {
                    mp.setValue(notNull(mp.getValue()));
                    mp.setAffPropertyValue(notNull(mp.getAffPropertyValue()));
                }

                String dlFormula = setting.getDlFormula();
                String dlColumn = setting.getDlColumn();
                String dlAffFormula = setting.getDlAffFormula();
                String dlAffColumn = setting.getDlAffColumn();
                String dlFormula2 = setting.getDlFormula2();
                String dlColumn2 = setting.getDlColumn2();

                Object mesConvertVal = expressionUtils.getValueFromFormula(mp, dlFormula);
                Object mesConvertValAff = expressionUtils.getValueFromFormula(mp, dlAffFormula);
                Object mesConvertVal2 = expressionUtils.getValueFromFormula(mp, dlFormula2);

                expressionUtils.setValueFromFormula(wt, dlColumn, mesConvertVal);
                expressionUtils.setValueFromFormula(wt, dlAffColumn, mesConvertValAff);
                expressionUtils.setValueFromFormula(wt, dlColumn2, mesConvertVal2);
            } catch (Exception e) {
                errorFields.put(setting.getMatPropNo() + ":==", e.getMessage());
            }
        });
        if (!errorFields.isEmpty()) {
            throw new Exception(wt.getModelName() + " 屬性從MES讀取失敗: " + errorFields.toString());
        }
    }

    private String notNull(String s) {
        return s == null ? "" : s;
    }

    private Worktime dlMtdTest(Worktime wt) throws Exception {
        List<MtdTestIntegrity> mtdTestIntegritys = mtdTestIntegrityQueryPort.queryM(wt, Factory.TWM3);

        MtdTestIntegrity t1TestIntegrity = mtdTestIntegritys.stream().filter(t -> "T1".equals(t.getStationName())).findFirst().orElse(null);
        MtdTestIntegrity t2TestIntegrity = mtdTestIntegritys.stream().filter(t -> "T2".equals(t.getStationName())).findFirst().orElse(null);
//        List<Integer> t1TestQty = t1TestIntegrity == null ? newArrayList(0, 0) : newArrayList(t1TestIntegrity.getStateCnt(), t1TestIntegrity.getItemCnt());
//        List<Integer> t2TestQty = t2TestIntegrity == null ? newArrayList(0, 0) : newArrayList(t2TestIntegrity.getStateCnt(), t2TestIntegrity.getItemCnt());

        String t1_isAutotest = t1TestIntegrity == null || t1TestIntegrity.getIsautotest() == null ? "Y" : t1TestIntegrity.getIsautotest();
        String t2_isAutotest = t2TestIntegrity == null || t2TestIntegrity.getIsautotest() == null ? "Y" : t2TestIntegrity.getIsautotest();

//        wt.setT1StatusQty(t1TestQty.get(0));
//        wt.setT1ItemsQty(t1TestQty.get(1));
        wt.setT1Autotest(t1_isAutotest);
//        wt.setT2StatusQty(t2TestQty.get(0));
//        wt.setT2ItemsQty(t2TestQty.get(1));
        wt.setT2Autotest(t2_isAutotest);

        return wt;
    }

    private <T extends Object> Map<String, T> toSelectOptions(List l) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map m = new HashMap();
        if (!l.isEmpty()) {
            Object firstObj = l.get(0);
            boolean isUserObject = firstObj instanceof IUserM9;
            for (Object obj : l) {
                String name = (String) PropertyUtils.getProperty(obj, isUserObject ? "jobnumber" : "name");
                m.put(isUserObject ? name.toUpperCase() : name, obj);
            }
        }
        return m;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
