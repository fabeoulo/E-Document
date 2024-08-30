/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice.download;

import com.advantech.helper.SpringExpressionUtils;
import com.advantech.model.db2.CartonLabelM4f;
import com.advantech.model.db2.OutLabelM4f;
import com.advantech.model.db2.PendingM4f;
import com.advantech.model.db2.WorktimeM4f;
import com.advantech.model.db2.WorktimeMaterialPropertyDownloadSettingM4f;
import com.advantech.service.db2.CartonLabelM4fService;
import com.advantech.service.db2.OutLabelM4fService;
import com.advantech.service.db2.PendingM4fService;
import com.advantech.service.db2.WorktimeMaterialPropertyDownloadSettingM4fService;
import com.advantech.webservice.Factory;
import com.advantech.webservice.port.MaterialPropertyValueQueryPort;
import com.advantech.webservice.unmarshallclass.MaterialPropertyValue;
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
public class MaterialPropertyValueM4fDownload extends BasicM4fDownload {

    private static final Logger logger = LoggerFactory.getLogger(MaterialPropertyValueM4fDownload.class);

    @Autowired
    private MaterialPropertyValueQueryPort materialPropertyValueQueryPort;

    @Autowired
    private WorktimeMaterialPropertyDownloadSettingM4fService propSettingService;

    private List<WorktimeMaterialPropertyDownloadSettingM4f> settings = new ArrayList();

    @Autowired
    private SpringExpressionUtils expressionUtils;

    @Autowired
    private PendingM4fService pendingService;

    @Autowired
    private CartonLabelM4fService cartonLabelService;

    @Autowired
    private OutLabelM4fService outLabelService;

    private Map<String, PendingM4f> pendingOptions;

    private Map<String, OutLabelM4f> outLabelOptions;

    private Map<String, CartonLabelM4f> cartonLabelOptions;

    public void initOptions() {
        try {
            pendingOptions = toSelectOptions(pendingService.findAll());
            outLabelOptions = toSelectOptions(outLabelService.findAll());
            cartonLabelOptions = toSelectOptions(cartonLabelService.findAll());

            settings = propSettingService.findAll();
            expressionUtils.setVariable("pendingMap", pendingOptions);
            expressionUtils.setVariable("outLabelMap", outLabelOptions);
            expressionUtils.setVariable("cartonLabelMap", cartonLabelOptions);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public WorktimeM4f download(WorktimeM4f wt) throws Exception {
        List<MaterialPropertyValue> remotePropSettings = materialPropertyValueQueryPort.queryM(wt, Factory.TWM9);
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
            } catch (Exception e) {
                errorFields.put(setting.getMatPropNo() + ":==", e.getMessage());
            }
        });
        if (!errorFields.isEmpty()) {
            throw new Exception(wt.getModelName() + " 屬性從MES讀取失敗: " + errorFields.toString());
        }

        return wt;
    }
    
    private String notNull(String s) {
        return s == null ? "" : s;
    }
}
