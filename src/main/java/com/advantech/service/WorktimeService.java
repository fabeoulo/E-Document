/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service;

import com.advantech.dao.*;
import com.advantech.helper.SpringExpressionUtils;
import com.advantech.helper.WorktimeValidator;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.Cobot;
import com.advantech.model.Worktime;
import com.advantech.model.WorktimeFormulaSetting;
import com.advantech.model.WorktimeLevelSetting;
import com.advantech.model.WorktimeSettingBase;
import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.Lists.newArrayList;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wei.Cheng
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WorktimeService extends BasicServiceImpl<Integer, Worktime> {

    @Autowired
    private WorktimeDAO dao;

    @Autowired
    private WorktimeFormulaSettingDAO worktimeFormulaSettingService;

    @Autowired
    private WorktimeLevelSettingDAO worktimeLevelSettingService;

    private final double levelRatio = 0.1, levelWeight = 0;

    @Autowired
    private WorktimeUploadMesService uploadMesService;

    @Autowired
    private WorktimeValidator validator;

    @Autowired
    private SpringExpressionUtils expressionUtils;

    @Autowired
    private WorktimeAuditService worktimeAuditService;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<Worktime> findAll(PageInfo info) {
        trimSearchString(info);
        List<Worktime> l = dao.findAll(info);
        l.forEach(w -> Hibernate.initialize(w.getCobots()));
        return l;
    }

    public Worktime findByModel(String modelName) {
        return dao.findByModel(modelName);
    }

    public Set<Cobot> findCobots(int obj_id) {
        Worktime w = this.findByPrimaryKey(obj_id);
        Set result = w.getCobots();
        Hibernate.initialize(result);
        return result;
    }

    public List<Worktime> findWithFlowRelation() {
        List<Worktime> result = dao.findAll();
        result.forEach(w -> {
            Hibernate.initialize(w.getFlowByBabFlowId());
            Hibernate.initialize(w.getFlowByTestFlowId());
            Hibernate.initialize(w.getFlowByPackingFlowId());
        });
        return result;
    }

    public List<Worktime> findWithFlowRelationAndCobot(Integer... ids) {
        List<Worktime> result = dao.findByPrimaryKeys(ids);
        result.forEach(w -> {
            Hibernate.initialize(w.getFlowByBabFlowId());
            Hibernate.initialize(w.getFlowByTestFlowId());
            Hibernate.initialize(w.getFlowByPackingFlowId());
            Hibernate.initialize(w.getCobots());
        });
        return result;
    }

    public List<Worktime> findWithFullRelation(PageInfo info) {
        trimSearchString(info);
        List<Worktime> result = dao.findWithFullRelation(info);
        result.forEach(w -> Hibernate.initialize(w.getCobots()));
        return result;
    }

    private void trimSearchString(PageInfo info) {
        if (info.getSearchString() != null && !"".equals(info.getSearchString())) {
            info.setSearchString(info.getSearchString().trim());
        }
    }

    public int insertWithMesUpload(List<Worktime> l) throws Exception {
        uploadMesService.portParamInit();
        int i = 1;
        for (Worktime w : l) {
            dao.insert(w);
            uploadMesService.insert(w);
            flushIfReachFetchSize(i++);
        }
        return 1;
    }

    public int insertWithMesUpload(Worktime worktime) throws Exception {
        return this.insertWithMesUpload(newArrayList(worktime));
    }

    //For batch modify.
    public int insertWithMesUploadAndFormulaSetting(List<Worktime> l) throws Exception {
        uploadMesService.portParamInit();
        int i = 0;
        for (Worktime w : l) {
            initUnfilledFormulaColumn(w);
            WorktimeFormulaSetting setting = w.getWorktimeFormulaSettings().get(0);
            WorktimeLevelSetting settingLevel = w.getWorktimeLevelSettings().get(0);
            w.setWorktimeFormulaSettings(null);
            w.setWorktimeLevelSettings(null);
            dao.insert(w);

            setting.setWorktime(w);
            worktimeFormulaSettingService.insert(setting);
            settingLevel.setWorktime(w);
            worktimeLevelSettingService.insert(settingLevel);

            uploadMesService.insert(w);
            flushIfReachFetchSize(i++);
        }
        return 1;
    }

    //For jqgrid edit(single row CRUD)
    public int insertWithFormulaSetting(Worktime worktime) throws Exception {
        return this.insertWithMesUploadAndFormulaSetting(newArrayList(worktime));
    }

    public int insertSeriesWithMesUpload(String baseModelName, List<String> seriesModelNames) throws Exception {
        //Insert worktime then insert worktimeFormulaSetting & cobots setting

        Worktime baseW = this.findByModel(baseModelName);
        checkArgument(baseW != null, "Can't find modelName: " + baseModelName);

        List<Worktime> l = new ArrayList();
        for (String seriesModelName : seriesModelNames) {
            Worktime cloneW = (Worktime) BeanUtils.cloneBean(baseW);
            cloneW.setId(0); //CloneW is a new row, reset id.
            cloneW.setModelName(seriesModelName);

            //Remove relation from FK models
            cloneW.setWorktimeFormulaSettings(null);
            cloneW.setWorktimeLevelSettings(null); // Fix shared references to a collection.
            cloneW.setBwFields(null);
            cloneW.setCobots(null);

            cloneW.setSplitFlag(1);

            l.add(cloneW);
        }

        validator.checkModelNameExists(l);
        this.insertWithMesUpload(l);

        //Insert worktimeFormulaSetting
        List<WorktimeFormulaSetting> formulaSettings = baseW.getWorktimeFormulaSettings();
        List<WorktimeLevelSetting> levelSettings = baseW.getWorktimeLevelSettings();
        checkState(!formulaSettings.isEmpty(), "Can't find formulaSetting on: " + baseModelName);
        checkState(!levelSettings.isEmpty(), "Can't find levelSetting on: " + baseModelName);
        WorktimeFormulaSetting baseWSetting = formulaSettings.get(0);
        WorktimeLevelSetting baseWSettingLevel = levelSettings.get(0);
        for (Worktime w : l) {
            WorktimeFormulaSetting cloneSetting = (WorktimeFormulaSetting) BeanUtils.cloneBean(baseWSetting);
            cloneSetting.setWorktime(w);
            worktimeFormulaSettingService.insert(cloneSetting);

            WorktimeLevelSetting cloneSettingLevel = (WorktimeLevelSetting) BeanUtils.cloneBean(baseWSettingLevel);
            cloneSettingLevel.setWorktime(w);
            worktimeLevelSettingService.insert(cloneSettingLevel);
        }

        //Insert cobots setting
        Set<Cobot> cobots = baseW.getCobots();
        Set<Cobot> cloneCobotsSetting = new HashSet<>();
        for (Cobot c : cobots) {
            Cobot cloneCobot = (Cobot) BeanUtils.cloneBean(c);
            cloneCobotsSetting.add(cloneCobot);
        }

        l.forEach(w -> {
            w.setCobots(cloneCobotsSetting);
            dao.update(w);
        });

        return 1;
    }

    public int updateWithMesUpload(List<Worktime> l) throws Exception {
        uploadMesService.portParamInit();
        int i = 1;
        for (Worktime w : l) {
            initUnfilledFormulaColumn(w);
            dao.update(w);
            worktimeFormulaSettingService.update(w.getWorktimeFormulaSettings().get(0));
            worktimeLevelSettingService.update(w.getWorktimeLevelSettings().get(0));
            uploadMesService.update(w);
            flushIfReachFetchSize(i++);
        }
        return 1;
    }

    public int updateWithMesUpload(Worktime worktime) throws Exception {
        return this.updateWithMesUpload(newArrayList(worktime));
    }

    public int mergeWithMesUpload(List<Worktime> l) throws Exception {
        uploadMesService.portParamInit();
        int i = 1;
        for (Worktime w : l) {
            initUnfilledFormulaColumn(w);
            checkWorkTimeSplit(w);

            worktimeFormulaSettingService.update(w.getWorktimeFormulaSettings().get(0));
            worktimeLevelSettingService.update(w.getWorktimeLevelSettings().get(0));
            dao.merge(w);
            uploadMesService.update(w);
            flushIfReachFetchSize(i++);
        }
        return 1;
    }

    public int mergeWithMesUpload(Worktime worktime) throws Exception {
        return this.mergeWithMesUpload(newArrayList(worktime));
    }

    public int insertByExcel(List<Worktime> l) throws Exception {
        l.forEach(w -> {
            w.setWorktimeFormulaSettings(newArrayList(new WorktimeFormulaSetting()));
            w.setWorktimeLevelSettings(newArrayList(new WorktimeLevelSetting()));
        });
        this.insertWithMesUploadAndFormulaSetting(l);
        return 1;
    }

    public int mergeByExcel(List<Worktime> l) throws Exception {
        retriveFormulaAndLevelSetting(l);

        uploadMesService.portParamInit();
        int i = 1;
        for (Worktime w : l) {
            //Don't need to update formula, but still need to re-calculate the formula field
            this.initUnfilledFormulaColumn(w);
            checkWorkTimeSplit(w);

            dao.merge(w);
            uploadMesService.update(w);
            flushIfReachFetchSize(i++);
        }
        return 1;

    }

    private void retriveFormulaAndLevelSetting(List<Worktime> l) {
        //Retrive settings because excel doesn't have formula setting field.
        List<WorktimeFormulaSetting> settings = worktimeFormulaSettingService.findAll();
        Map<Integer, WorktimeFormulaSetting> settingMap = getSettingMap(settings);

        List<WorktimeLevelSetting> levelSettings = worktimeLevelSettingService.findAll();
        Map<Integer, WorktimeLevelSetting> levelSettingMap = getSettingMap(levelSettings);

        l.forEach((w) -> {
            w.setWorktimeFormulaSettings(newArrayList(settingMap.get(w.getId())));
            w.setWorktimeLevelSettings(newArrayList(levelSettingMap.get(w.getId())));
        });
    }

    private <T extends WorktimeSettingBase> Map<Integer, T> getSettingMap(List<T> settings) {
        Map settingMap = new HashMap();
        settings.forEach((setting) -> {
            settingMap.put(setting.getWorktime().getId(), setting);
        });
        return settingMap;
    }

    private boolean isWorktimeSplit(Worktime current) {
        return sumWorkTime(
                current.getAssy2(), current.getSeal1(),
                current.getOpticalBonding1(), current.getOpticalBonding2(),
                current.getPressureCookerCost()
        ).compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isProductionWtChanged(Worktime prev, Worktime current) {
        return isModelNameChanged(prev, current)
                || !isEquals(prev.getProductionWt(), current.getProductionWt());
    }

    private boolean isSealChanged(Worktime prev, Worktime current) {
        return isModelNameChanged(prev, current)
                || !isEquals(sumWorkTime(prev.getSeal(), prev.getSeal1()),
                        sumWorkTime(current.getSeal(), current.getSeal1()));
    }

    private boolean isBiCostChanged(Worktime prev, Worktime current) {
        return isModelNameChanged(prev, current)
                || !isEquals(sumWorkTime(prev.getPressureCookerCost(), prev.getBiCost()),
                        sumWorkTime(current.getPressureCookerCost(), current.getBiCost()));
    }

    private boolean isAssyChanged(Worktime prev, Worktime current) {
        return isModelNameChanged(prev, current)
                || !isEquals(sumWorkTime(prev.getAssy(), prev.getBondedSealingFrame(), prev.getAssy2()),
                        sumWorkTime(current.getAssy(), current.getBondedSealingFrame(), current.getAssy2()));
    }

    private boolean isModelNameChanged(Worktime prev, Worktime current) {
        return !prev.getModelName().equals(current.getModelName());
    }

    private <T extends Comparable> boolean isEquals(T o1, T o2) {
        return ObjectUtils.compare(o1, o2) == 0;
    }

    private BigDecimal sumWorkTime(BigDecimal... params) {
        return Arrays.stream(params)
                .map(param -> (param == null || param.compareTo(BigDecimal.ZERO) < 0) ? BigDecimal.ZERO : param)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void initUnfilledFormulaColumn(Worktime w) {
        //Lazy loading
        WorktimeFormulaSetting setting = w.getWorktimeFormulaSettings().get(0);

        if (isColumnCalculated(setting.getProductionWt())) {
            BigDecimal levelTime = getCleanRoomLevelTime(w);
            w.setLevelProductWt(levelTime);
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
        if (isColumnCalculated(setting.getAssyLeadTime())) {
            w.setDefaultAssyLeadTime();
        }
        if (isColumnCalculated(setting.getTest())) {
            w.setDefaultTest();
        }
        if (isColumnCalculated(setting.getMachineWorktime())) {
            //Set machine worktime
            w = setCobotWorktime(w);
        }
    }

    private void checkWorkTimeSplit(Worktime w) {
        if (w.getSplitFlag() == 0 && isWorktimeSplit(w)) {
            Worktime rowLastStatus = worktimeAuditService.findLastStatusBeforeUpdate(w.getId());
            String model = w.getModelName();
//            checkState(!isSealChanged(rowLastStatus, w), model + " 初次拆分工時，Seal部分不一致");
//            checkState(!isBiCostChanged(rowLastStatus, w), model + " 初次拆分工時，壓力鍋Cost不一致");
//            checkState(!isAssyChanged(rowLastStatus, w), model + " 初次拆分工時，ASSY部分不一致");
            checkState(!isProductionWtChanged(rowLastStatus, w), model + " 初次拆分工時，ProductionWt不一致");

            w.setSplitFlag(1);
        }
    }

    private boolean isColumnCalculated(int i) {
        return i == 1;
    }

    private BigDecimal getCleanRoomLevelTime(Worktime w) {
        List<WorktimeLevelSetting> settings = w.getWorktimeLevelSettings();
        checkState(!settings.isEmpty(), "No WorktimeLevelSetting : " + w.getModelName());
        Map<String, Integer> settingMap = settings.get(0).retriveIntPropertiesMap();
        return countStandardLevelTime(settingMap, w);
    }

    private BigDecimal countStandardLevelTime(Map<String, Integer> settingMap, Worktime w) {
        BigDecimal levelTime = BigDecimal.ZERO;
        for (Map.Entry<String, Integer> entry : settingMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (isColumnCalculated(value) && !key.equalsIgnoreCase("id")) {
                try {
                    Field field = Worktime.class.getDeclaredField(key);
                    field.setAccessible(true); // Allow access to private fields
                    if (field.getType() == BigDecimal.class) {
                        BigDecimal wt = (BigDecimal) field.get(w);
                        levelTime = levelTime.add(
                                wt.multiply(new BigDecimal(levelRatio))
                                        .add(new BigDecimal(levelWeight))
                        );
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return levelTime;
    }

    public Worktime setCobotWorktime(Worktime w) {
        //Find cobots setting if cobots is not provide when user use excel batch update model
        Set<Cobot> cobots = w.getCobots() == null ? this.findCobots(w.getId()) : w.getCobots();
        BigDecimal machineWorktime = BigDecimal.ZERO;
        if (cobots != null && !cobots.isEmpty()) {
            machineWorktime = cobots.stream()
                    .map(x -> {
                        return (BigDecimal) expressionUtils.getValueFromFormula(w, x.getFormula());
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(1, RoundingMode.HALF_UP);
        }
        w.setMachineWorktime(machineWorktime);

        return w;
    }

//    //For sysop batch insert data into database.
//    public int saveOrUpdate(List<Worktime> l) throws Exception {
//        for (int i = 0; i < l.size(); i++) {
//            Worktime w = l.get(i);
//            Worktime existW = this.findByModel(w.getModelName());
//            if (existW == null) {
//                this.insertWithFormulaSetting(w);
//            } else {
//                w.setId(existW.getId());
//                this.mergeWithMesUpload(w);
//            }
//        }
//        return 1;
//    }
    public int deleteWithMesUpload(List<Worktime> l) throws Exception {
        uploadMesService.portParamInit();
        int i = 1;
        for (Worktime w : l) {
            dao.delete(w);
            uploadMesService.delete(w);
            flushIfReachFetchSize(i++);
        }
        return 1;
    }

    public int deleteWithMesUpload(Worktime w) throws Exception {
        return this.deleteWithMesUpload(newArrayList(w));
    }

    public int deleteWithMesUpload(Integer... ids) throws Exception {
        List<Worktime> worktimes = dao.findByPrimaryKeys(ids);
        return this.deleteWithMesUpload(worktimes);
    }

    public int deleteWithMesUpload(int id) throws Exception {
        Worktime worktime = this.findByPrimaryKey(id);
        dao.delete(worktime);
        uploadMesService.portParamInit();
        uploadMesService.delete(worktime);
        return 1;
    }

    public void reUpdateAllFormulaColumn() throws Exception {
        List<Worktime> l = this.findAll();
        this.mergeWithMesUpload(l);
    }

}
