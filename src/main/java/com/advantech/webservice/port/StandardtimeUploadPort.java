/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice.port;

import com.advantech.helper.SpringExpressionUtils;
import com.advantech.model.Worktime;
import com.advantech.model.WorktimeAutouploadSetting;
import com.advantech.service.WorktimeAutouploadSettingService;
import com.advantech.webservice.root.StandardtimeRoot;
import com.advantech.webservice.unmarshallclass.StandardWorkTime;
import java.math.BigDecimal;
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
 * @author Wei.Cheng CRUD都用同個XML & 相同 UploadType "A"// not same UploadType
 * 20230707
 */
@Component
public class StandardtimeUploadPort extends BasicUploadPort implements UploadPort {

    private static final Logger logger = LoggerFactory.getLogger(StandardtimeUploadPort.class);
    private List<WorktimeAutouploadSetting> settings;

    @Autowired
    private SpringExpressionUtils expressionUtils;

    @Autowired
    private WorktimeAutouploadSettingService settingService;

    @Autowired
    private StandardWorkTimeQueryPort worktimeQueryPort;

    @Override
    protected void initJaxbMarshaller() {
        try {
            super.initJaxbMarshaller(StandardtimeRoot.class); //To change body of generated methods, choose Tools | Templates.
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    //Don't forget init setting everytime.
    public void initSettings() {
        settings = settingService.findAll();

    }

    public void initSettings(List<WorktimeAutouploadSetting> settings) {
        this.settings = settings;

    }

    @Override
    public void insert(Worktime w) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override//245
    public void update(Worktime w) throws Exception {
        Map<String, String> errorFields = new HashMap();
        List<StandardWorkTime> standardWorktimes = worktimeQueryPort.query(w.getModelName());

        settings.forEach((setting) -> {
            try {
                StandardWorkTime worktimeOnMes = standardWorktimes.stream()
                        .filter(p -> (Objects.equals(p.getSTATIONID(), setting.getStationId()) || (p.getSTATIONID() == -1 && setting.getStationId() == null))
                        && (p.getLINEID() == setting.getLineId())
                        && Objects.equals(p.getUNITNO(), setting.getColumnUnit())
                        && Objects.equals(p.getITEMNO(), w.getModelName()))
                        .findFirst().orElse(null);
                this.generateRootAndUpload(setting, worktimeOnMes, w);
            } catch (Exception e) {
                errorFields.put(setting.getColumnName(), e.getMessage());
            }
        });
        if (!errorFields.isEmpty()) {
            throw new Exception(errorFields.toString());
        }
    }

    @Override
    public void delete(Worktime w) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 

//dont execute it.//!!! import!!! be careful.
//        //dont execute it.//!!! import!!! be careful.
//        Map<String, String> errorFields = new HashMap();
//        List<StandardWorkTime> standardWorktimes = worktimeQueryPort.query(w.getModelName());
//
//        settings.forEach((setting) -> {
//            try {
//                StandardWorkTime worktimeOnMes = standardWorktimes.stream()
//                        .filter(p -> (Objects.equals(p.getSTATIONID(), setting.getStationId()) || (p.getSTATIONID() == -1 && setting.getStationId() == null))
//                        && (p.getLINEID() == setting.getLineId())
//                        && Objects.equals(p.getUNITNO(), setting.getColumnUnit())
//                        && Objects.equals(p.getITEMNO(), w.getModelName()))
//                        .findFirst().orElse(null);
//                this.generateRootAndDelete(setting, worktimeOnMes, w);
//            } catch (Exception e) {
//                errorFields.put(setting.getColumnName(), e.getMessage());
//            }
//        });
//        if (!errorFields.isEmpty()) {
//            throw new Exception(errorFields.toString());
//        }
    }

    private void generateRootAndUpload(WorktimeAutouploadSetting setting, StandardWorkTime standardWorktime, Worktime w) throws Exception {
        String columnUnit = setting.getColumnUnit();
        BigDecimal totalCt = (BigDecimal) expressionUtils.getValueFromFormula(w, setting.getFormula());
        BigDecimal changeTimeCt = (BigDecimal) expressionUtils.getValueFromFormula(w, setting.getFormulaCt());

        if (isCtChanged(totalCt, changeTimeCt, standardWorktime)) {
            StandardtimeRoot root = new StandardtimeRoot();
            StandardtimeRoot.STANDARDWORKTIME swt = root.getSTANDARDWORKTIME();
            swt.setSTANDARDID(standardWorktime == null ? 0 : standardWorktime.getSTANDARDID());
            swt.setUNITNO(columnUnit);
            swt.setSTATIONID(setting.getStationId() == null ? -1 : setting.getStationId());
            swt.setLINEID(setting.getLineId());
            swt.setITEMNO(w.getModelName());
            swt.setTOTALCT(totalCt);
            swt.setFIRSTTIME(BigDecimal.ZERO);
            swt.setCT(changeTimeCt);
            swt.setSIDE(5010);
            swt.setMIXCT(totalCt); //Temporarily set this column equal to totalCt
            swt.setCHANGEREASONNO(w.getReasonCode());

            if ("B".equals(columnUnit) && setting.getStationId() != null) {
                swt.setMACHINECNT(w.getAssyStation());
                swt.setOPCNT(w.getAssyStation());

                //Auto fill reason code when only change assyStation or packingStation
                if (Objects.equals(w.getAssy(), totalCt)
                        && isAutoReason(w.getAssyStation(), standardWorktime, w)) {
                    swt.setCHANGEREASONNO("A3");
                }
            } else if ("P".equals(columnUnit) && setting.getStationId() != null) {
                swt.setMACHINECNT(w.getPackingStation());
                swt.setOPCNT(w.getPackingStation());

                if (Objects.equals(w.getPacking(), totalCt)
                        && isAutoReason(w.getPackingStation(), standardWorktime, w)) {
                    swt.setCHANGEREASONNO("A3");
                }
            } else {
                swt.setMACHINECNT(1);
                swt.setOPCNT(1);
            }
            super.upload(root, standardWorktime == null ? UploadType.INSERT : UploadType.UPDATE);
        }
    }

    private boolean isCtChanged(BigDecimal totalCt, BigDecimal changeTimeCt, StandardWorkTime s) {
        return s == null || totalCt.compareTo(s.getTOTALCT()) != 0 || changeTimeCt.compareTo(s.getCT()) != 0;
    }

    private boolean isAutoReason(Integer stationPeople, StandardWorkTime standardWorktime, Worktime w) {
        return standardWorktime != null
                && !Objects.equals(stationPeople, standardWorktime.getOPCNT())
                && (w.getReasonCode() == null || "0".equals(w.getReasonCode()));
    }

    private void generateRootAndDelete(WorktimeAutouploadSetting setting, StandardWorkTime standardWorktime, Worktime w) throws Exception {
        throw new UnsupportedOperationException("dont execute it."); 
//        String columnUnit = setting.getColumnUnit();
//
//        StandardtimeRoot root = new StandardtimeRoot();
//        StandardtimeRoot.STANDARDWORKTIME swt = root.getSTANDARDWORKTIME();
//        swt.setUNITNO(columnUnit);
//        swt.setSTATIONID(setting.getStationId() == null ? -1 : setting.getStationId());
//        swt.setLINEID(setting.getLineId());
//        swt.setITEMNO(w.getModelName());
//        swt.setSIDE(5010);
//
//        super.upload(root, UploadType.DELETE);
    }

}
