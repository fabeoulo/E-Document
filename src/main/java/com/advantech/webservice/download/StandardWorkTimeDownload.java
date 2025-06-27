/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.webservice.download;

import com.advantech.helper.SpringExpressionUtils;
import com.advantech.model.WorktimeAutodownloadSetting;
import com.advantech.model.Worktime;
import com.advantech.service.WorktimeAutodownloadSettingService;
import com.advantech.webservice.Factory;
import com.advantech.webservice.download.db2.BasicM4fDownload;
import com.advantech.webservice.port.StandardWorkTimeQueryPort;
import com.advantech.webservice.unmarshallclass.StandardWorkTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Justin.Yeh
 */
@Component
public class StandardWorkTimeDownload extends BasicM4fDownload<Worktime> {

    private static final Logger logger = LoggerFactory.getLogger(StandardWorkTimeDownload.class);

    @Autowired
    private StandardWorkTimeQueryPort worktimeQueryPort;

    @Autowired
    private WorktimeAutodownloadSettingService propSettingService;

    private List<WorktimeAutodownloadSetting> settings = new ArrayList();

    @Autowired
    private SpringExpressionUtils expressionUtils;

    public void initOptions() {
        try {
            settings = propSettingService.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Worktime download(Worktime wt) throws Exception {
        Map<String, String> errorFields = new HashMap();

        List<StandardWorkTime> standardWorktimes = new ArrayList<>();
        try {
            standardWorktimes = worktimeQueryPort.queryM(wt.getModelName(), Factory.TWM3);
        } catch (Exception e) {
            errorFields.put(worktimeQueryPort.getClass().getName(), e.getMessage());
        }

        wt.setAssyStation(1);
        wt.setPackingStation(1);
        for (WorktimeAutodownloadSetting setting : settings) {
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
                    Integer opcnt = worktimeOnMes.getOPCNT();
                    if ("B".equals(columnUnit) && setting.getStationId() != null && opcnt > 0) {
                        wt.setAssyStation(opcnt);
                    } else if ("P".equals(columnUnit) && setting.getStationId() != null && opcnt > 0) {
                        wt.setPackingStation(opcnt);
                    }
                }
            } catch (Exception e) {
                errorFields.put(setting.getColumnName(), e.getMessage());
            }
        }

        if (!errorFields.isEmpty()) {
            throw new Exception(wt.getModelName() + " 工時從MES讀取失敗: " + errorFields.toString());
        }

        return wt;
    }
}
