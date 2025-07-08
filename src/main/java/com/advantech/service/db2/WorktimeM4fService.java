/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.service.db2;

import com.advantech.dao.db2.*;
import com.advantech.helper.SpringExpressionUtils;
import com.advantech.helper.WorktimeM4fValidator;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.BusinessGroupM4f;
import com.advantech.model.db2.CobotM4f;
import com.advantech.model.db2.IWorktimeForWebService;
import com.advantech.model.db2.WorktimeFormulaSettingM4f;
import com.advantech.model.db2.WorktimeM4f;
import com.advantech.service.WorktimeUploadMesService;
import com.google.common.base.Preconditions;
import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.Lists.newArrayList;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional(value = "tx2", rollbackFor = Exception.class)
public class WorktimeM4fService extends BasicServiceImpl<Integer, WorktimeM4f> {

    @Autowired
    private WorktimeM4fDAO dao;

    @Autowired
    private WorktimeFormulaSettingM4fDAO worktimeFormulaSettingDao;

//    @Autowired
//    private WorktimeUploadMesService uploadMesService;
//    
    @Autowired
    private WorktimeM4fValidator validator;

    @Autowired
    private BusinessGroupM4fService businessGroupService;

    @Autowired
    private SpringExpressionUtils expressionUtils;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<WorktimeM4f> findAll(PageInfo info) {
        trimSearchString(info);
        List<WorktimeM4f> l = dao.findAll(info);
        l.forEach(w -> Hibernate.initialize(w.getCobots()));
        return l;
    }

    public WorktimeM4f findByModel(String modelName) {
        List<WorktimeM4f> result = dao.findByModel(modelName);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<WorktimeM4f> findByModelNames(String... modelName) {
        return dao.findByModel(modelName);
    }

    public Set<CobotM4f> findCobots(int obj_id) {
        WorktimeM4f w = this.findByPrimaryKey(obj_id);
        Set result = w.getCobots();
        Hibernate.initialize(result);
        return result;
    }

    public List<WorktimeM4f> findWithFlowRelation() {
        List<WorktimeM4f> result = dao.findAll();
        result.forEach(w -> {
            Hibernate.initialize(w.getFlowByBabFlowId());
            Hibernate.initialize(w.getFlowByTestFlowId());
            Hibernate.initialize(w.getFlowByPackingFlowId());
        });
        return result;
    }

    public List<WorktimeM4f> findWithFlowRelationAndCobot(Integer... ids) {
        List<WorktimeM4f> result = dao.findByPrimaryKeys(ids);
        result.forEach(w -> {
            Hibernate.initialize(w.getFlowByBabFlowId());
            Hibernate.initialize(w.getFlowByTestFlowId());
            Hibernate.initialize(w.getFlowByPackingFlowId());
            Hibernate.initialize(w.getCobots());
        });
        return result;
    }

    public List<WorktimeM4f> findWithFullRelation(PageInfo info) {
        trimSearchString(info);
        List<WorktimeM4f> result = dao.findWithFullRelation(info);
        result.forEach(w -> Hibernate.initialize(w.getCobots()));
        return result;
    }

    public List<WorktimeM4f> findAllWithFormula() {
        return dao.findAllWithFormula();
    }

    private void trimSearchString(PageInfo info) {
        if (info.getSearchString() != null && !"".equals(info.getSearchString())) {
            info.setSearchString(info.getSearchString().trim());
        }
    }

    public int insertWithMesUpload(List<WorktimeM4f> l) throws Exception {
        //uploadMesService.portParamInit();
        int i = 1;
        for (WorktimeM4f w : l) {
            dao.insert(w);
//            uploadMesService.insert(w);
            flushIfReachFetchSize(i++);
        }
        return 1;
    }

//    public int insertWithMesUpload(WorktimeM4f worktime) throws Exception {
//        return this.insertWithMesUpload(newArrayList(worktime));
//    }
    //For batch modify.
    public int insertWithMesUploadAndFormulaSetting(List<WorktimeM4f> l) throws Exception {
        //uploadMesService.portParamInit();
        int i = 0;
        for (WorktimeM4f w : l) {
            initUnfilledFormulaColumn(w);
            WorktimeFormulaSettingM4f setting = w.getWorktimeFormulaSettings().get(0);
            w.setWorktimeFormulaSettings(null);
            dao.insert(w);
            setting.setWorktime(w);
            worktimeFormulaSettingDao.insert(setting);
//            uploadMesService.insert(w);
            flushIfReachFetchSize(i++);
        }
        return 1;
    }

    //For jqgrid edit(single row CRUD)
    public int insertWithFormulaSetting(WorktimeM4f worktime) throws Exception {
        return this.insertWithMesUploadAndFormulaSetting(newArrayList(worktime));
    }

    public int insertSeriesWithMesUpload(String baseModelName, List<String> seriesModelNames) throws Exception {
        //Insert worktime then insert worktimeFormulaSetting & cobots setting

        WorktimeM4f baseW = this.findByModel(baseModelName);
        checkArgument(baseW != null, "Can't find modelName: " + baseModelName);
        BusinessGroupM4f bg = this.getBaseBusinessGroup(baseW.getModelName());

        List<WorktimeM4f> l = new ArrayList();
        for (String seriesModelName : seriesModelNames) {
            WorktimeM4f cloneW = (WorktimeM4f) BeanUtils.cloneBean(baseW);
            cloneW.setId(0); //CloneW is a new row, reset id.
            cloneW.setModelName(seriesModelName);
            cloneW.setReasonCode(""); //Don't copy exist reason in base model
            cloneW.setWorktimeModReason("");

            //Remove relation from FK models
            cloneW.setWorktimeFormulaSettings(null);
            cloneW.setBwFields(null);
            cloneW.setCobots(null);

            //set NewModel Default
            cloneW.setPreAssyModuleQty(0);
            cloneW.setCobotManualWt(BigDecimal.ZERO);
            cloneW.setBusinessGroup(bg);
            cloneW.setWorkCenter(bg.getWorkCenter());

            l.add(cloneW);
        }

        validator.checkModelNameExists(l);
        this.insertWithMesUpload(l);

        //Insert worktimeFormulaSetting
        WorktimeFormulaSettingM4f baseWSetting = baseW.getWorktimeFormulaSettings().get(0);
        checkState(baseWSetting != null, "Can't find formulaSetting on: " + baseModelName);
        for (WorktimeM4f w : l) {
            WorktimeFormulaSettingM4f cloneSetting = (WorktimeFormulaSettingM4f) BeanUtils.cloneBean(baseWSetting);
            cloneSetting.setWorktime(w);
            worktimeFormulaSettingDao.insert(cloneSetting);
        }

        //Insert cobots setting
        Set<CobotM4f> cobots = baseW.getCobots();
        Set<CobotM4f> cloneCobotsSetting = new HashSet<>();
        for (CobotM4f c : cobots) {
            CobotM4f cloneCobot = (CobotM4f) BeanUtils.cloneBean(c);
            cloneCobotsSetting.add(cloneCobot);
        }

        l.forEach(w -> {
            w.setCobots(cloneCobotsSetting);
            dao.update(w);
        });

        return 1;
    }

    private BusinessGroupM4f getBaseBusinessGroup(String modelName) {
        Map<String, BusinessGroupM4f> mapBg = businessGroupService.getMapByNotDisable();
        BusinessGroupM4f bg = mapBg.getOrDefault("EDIS", new BusinessGroupM4f());
//        boolean isEs = modelName.endsWith("ES");
//        if (isEs) {
//            bg = mapBg.getOrDefault("ES", bg);
//        }
        return bg;
    }
//
//    public int updateWithMesUpload(List<WorktimeM4f> l) throws Exception {
//        //uploadMesService.portParamInit();
//        int i = 1;
//        for (WorktimeM4f w : l) {
//            initUnfilledFormulaColumn(w);
//            dao.update(w);
//            worktimeFormulaSettingDao.update(w.getWorktimeFormulaSettings().get(0));
////            uploadMesService.update(w);
//            flushIfReachFetchSize(i++);
//        }
//        return 1;
//    }
//
//    public int updateWithMesUpload(WorktimeM4f worktime) throws Exception {
//        return this.updateWithMesUpload(newArrayList(worktime));
//    }
//    

    public int mergeWithMesUpload(List<WorktimeM4f> l) throws Exception {
//        //uploadMesService.portParamInit();
        int i = 1;
        for (WorktimeM4f w : l) {
            initUnfilledFormulaColumn(w);
            worktimeFormulaSettingDao.update(w.getWorktimeFormulaSettings().get(0));
            dao.merge(w);
//            uploadMesService.update(w);
            flushIfReachFetchSize(i++);
        }
        return 1;
    }

    public int mergeWithMesUpload(WorktimeM4f worktime) throws Exception {
        return this.mergeWithMesUpload(newArrayList(worktime));
    }

    public int insertWithoutUpload(List<WorktimeM4f> l) {
        int i = 0;
        for (WorktimeM4f w : l) {
            initUnfilledFormulaColumn(w);
            WorktimeFormulaSettingM4f setting = w.getWorktimeFormulaSettings().get(0);
            w.setWorktimeFormulaSettings(null);
            dao.insert(w);
            setting.setWorktime(w);
            worktimeFormulaSettingDao.insert(setting);
            flushIfReachFetchSize(i++);
        }
        return 1;
    }

    public int insertByMesDL(List<WorktimeM4f> l) {
        List<WorktimeFormulaSettingM4f> formulas = newArrayList(new WorktimeFormulaSettingM4f());
        l.forEach(w -> {
            w.setWorktimeFormulaSettings((formulas));
        });
        this.insertWithoutUpload(l);
        return 1;
    }

    public int insertByMesDL(WorktimeM4f worktime) {
        return this.insertByMesDL(newArrayList(worktime));
    }

    public int mergeWithoutUpload(List<WorktimeM4f> l) {
        int i = 1;
        for (WorktimeM4f w : l) {
            initUnfilledFormulaColumn(w);
            dao.merge(w);
            flushIfReachFetchSize(i++);
        }
        return 1;
    }

    public void setNotNullFieldDefault(WorktimeM4f wt) throws Exception {
        Class clz = wt.getClass();
        Method[] methods = clz.getDeclaredMethods(); // includes super class method, i.e. getter may return diff type.

        for (Method method : methods) {
            if (isGetter(method)) {
                if (method.isAnnotationPresent(NotNull.class)) {

                    String fieldName = getFieldNameFromGetter(method);

                    Field field = clz.getDeclaredField(fieldName);
                    Preconditions.checkNotNull(field, "Cannot find field: " + fieldName);
                    field.setAccessible(true);
                    if (field.get(wt) == null) {
                        Object valueToSet = wt.getDefaultByType(method.getReturnType(), fieldName);
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

    public int insertByExcel(List<WorktimeM4f> l) throws Exception {
        l.forEach(w -> {
            w.setWorktimeFormulaSettings(newArrayList(new WorktimeFormulaSettingM4f()));
        });
        this.insertWithMesUploadAndFormulaSetting(l);
        return 1;
    }

    public int mergeByExcel(List<WorktimeM4f> l) {
        retriveFormulaSetting(l);

//        //uploadMesService.portParamInit();
        int i = 1;
        for (WorktimeM4f w : l) {
            //Don't need to update formula, but still need to re-calculate the formula field
            this.initUnfilledFormulaColumn(w);

            dao.merge(w);
//            uploadMesService.update(w);
            flushIfReachFetchSize(i++);
        }
        return 1;

    }

    private void retriveFormulaSetting(List<WorktimeM4f> l) {
        //Retrive settings because excel doesn't have formula setting field.
        List<WorktimeFormulaSettingM4f> settings = worktimeFormulaSettingDao.findWithWorktime();
        Map<Integer, WorktimeFormulaSettingM4f> settingMap = new HashMap();
        settings.forEach((setting) -> {
            settingMap.put(setting.getWorktime().getId(), setting);
        });

        l.forEach((w) -> {
            w.setWorktimeFormulaSettings(newArrayList(settingMap.get(w.getId())));
        });
    }

    public void initUnfilledFormulaColumn(WorktimeM4f w) {
        //Lazy loading
        WorktimeFormulaSettingM4f setting = w.getWorktimeFormulaSettings().get(0);

        if (isColumnCalculated(setting.getCleanPanelAndAssembly())) {
            w.setDefaultCleanPanelAndAssembly();
        }
        if (isColumnCalculated(setting.getProductionWt())) {
            w.setDefaultProductWt();
        }
        if (isColumnCalculated(setting.getSetupTime())) {
            w.setDefaultSetupTime();
        }
        if (isColumnCalculated(setting.getAssyToT1())) {
            w.setDefaultAssyToT1();
        }
        if (isColumnCalculated(setting.getT2ToPacking())) {
            w.setDefaultT2ToPacking();
        }
        if (isColumnCalculated(setting.getAssyStation())) {
            w.setDefaultAssyStation();
        }
        if (isColumnCalculated(setting.getPackingStation())) {
            w.setDefaultPackingStation();
        }
        if (isColumnCalculated(setting.getAssyKanbanTime())) {
            w.setDefaultAssyKanbanTime();
        }
        if (isColumnCalculated(setting.getPackingKanbanTime())) {
            w.setDefaultPackingKanbanTime();
        }
        if (isColumnCalculated(setting.getMachineWorktime())) {
            //Set machine worktime
            setCobotWorktime(w);
        }
        if (isColumnCalculated(setting.getCobotManualWt())) {
            setCobotManualWtFormula(w);
        }
    }

    private boolean isColumnCalculated(int i) {
        return i == 1;
    }

    public WorktimeM4f setCobotWorktime(WorktimeM4f w) {
        //Find cobots setting if cobots is not provide when user use excel batch update model
        Set<CobotM4f> cobots = w.getCobots() == null ? this.findCobots(w.getId()) : w.getCobots();
        BigDecimal machineWorktime = BigDecimal.ZERO;
        if (cobots != null && !cobots.isEmpty()) {
            machineWorktime = cobots.stream()
                    .map(x -> {
                        return (BigDecimal) expressionUtils.getValueFromFormula(w, x.getFormula());
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        }
        w.setMachineWorktime(machineWorktime);

        return w;
    }

    private WorktimeM4f setCobotManualWtFormula(WorktimeM4f w) {
        //Find cobots setting if cobots is not provide when user use excel batch update model
        Set<CobotM4f> cobots = w.getCobots() == null ? this.findCobots(w.getId()) : w.getCobots();
        BigDecimal cobotManualWt = BigDecimal.ZERO;
        if (w.getTwm2Flag() == 1) {
            cobotManualWt = w.getProductionWt();

            if (cobots != null && !cobots.isEmpty()
                    && cobots.stream().anyMatch(c -> c.getName().contains("ADAM"))) {
                WorktimeFormulaSettingM4f setting = w.getWorktimeFormulaSettings().get(0);
                setting.setCobotManualWt(0);
            }
        }
        w.setCobotManualWt(cobotManualWt);

        return w;
    }

    //For sysop batch insert data into database.
    public int saveOrUpdate(List<WorktimeM4f> l) throws Exception {
        for (int i = 0; i < l.size(); i++) {
            WorktimeM4f w = l.get(i);
            WorktimeM4f existW = this.findByModel(w.getModelName());
            if (existW == null) {
                this.insertWithFormulaSetting(w);
            } else {
                w.setId(existW.getId());
                this.mergeWithMesUpload(w);
            }
        }
        return 1;
    }

    public int deleteWithMesUpload(List<WorktimeM4f> l) throws Exception {
//        //uploadMesService.portParamInit();
        int i = 1;
        for (WorktimeM4f w : l) {
            dao.delete(w);
//            uploadMesService.delete(w);
            flushIfReachFetchSize(i++);
        }
        return 1;
    }

    public int deleteWithMesUpload(WorktimeM4f w) throws Exception {
        return this.deleteWithMesUpload(newArrayList(w));
    }

    public int deleteWithMesUpload(Integer... ids) throws Exception {
        List<WorktimeM4f> worktimes = dao.findByPrimaryKeys(ids);
        return this.deleteWithMesUpload(worktimes);
    }

    public int deleteWithMesUpload(int id) throws Exception {
        WorktimeM4f worktime = this.findByPrimaryKey(id);
        dao.delete(worktime);
//        //uploadMesService.portParamInit();
//        uploadMesService.delete(worktime);
        return 1;
    }

    public void reUpdateAllFormulaColumn() throws Exception {
        List<WorktimeM4f> l = this.findAll();
        this.mergeWithMesUpload(l);
    }

}
