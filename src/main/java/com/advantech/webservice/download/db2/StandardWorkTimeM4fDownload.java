/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.webservice.download.db2;

import com.advantech.helper.SpringExpressionUtils;
import com.advantech.model.db2.WorktimeAutodownloadSettingM4f;
import com.advantech.model.db2.WorktimeM4f;
import com.advantech.service.db2.WorktimeAutodownloadSettingM4fService;
import com.advantech.webservice.Factory;
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
public class StandardWorkTimeM4fDownload extends BasicM4fDownload<WorktimeM4f> {

    private static final Logger logger = LoggerFactory.getLogger(StandardWorkTimeM4fDownload.class);

    @Autowired
    private StandardWorkTimeQueryPort worktimeQueryPort;

    @Autowired
    private WorktimeAutodownloadSettingM4fService propSettingService;

    private List<WorktimeAutodownloadSettingM4f> settings = new ArrayList();

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
    public WorktimeM4f download(WorktimeM4f wt) throws Exception {
        List<StandardWorkTime> standardWorktimes = worktimeQueryPort.queryM(wt.getModelName(), Factory.TWM9);
        Map<String, String> errorFields = new HashMap();

        wt.setAssyStation(1);
        wt.setPackingStation(1);
        settings.forEach((setting) -> {
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
                        wt.setAssyStation(worktimeOnMes.getOPCNT());
                    } else if ("P".equals(columnUnit) && setting.getStationId() != null && opcnt > 0) {
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
}
