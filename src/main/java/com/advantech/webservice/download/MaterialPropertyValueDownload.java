/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice.download;

import com.advantech.helper.SpringExpressionUtils;
import com.advantech.model.Pending;
import com.advantech.model.Worktime;
import com.advantech.model.WorktimeMaterialPropertyDownloadSetting;
import com.advantech.service.PendingService;
import com.advantech.service.WorktimeMaterialPropertyDownloadSettingService;
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
public class MaterialPropertyValueDownload extends BasicM4fDownload<Worktime> {

    private static final Logger logger = LoggerFactory.getLogger(MaterialPropertyValueDownload.class);

    @Autowired
    private MaterialPropertyValueQueryPort materialPropertyValueQueryPort;

    @Autowired
    private WorktimeMaterialPropertyDownloadSettingService propSettingService;

    private List<WorktimeMaterialPropertyDownloadSetting> settings = new ArrayList();

    @Autowired
    private SpringExpressionUtils expressionUtils;

    @Autowired
    private PendingService pendingService;

    private Map<String, Pending> pendingOptions;

    public void initOptions() {
        try {
            pendingOptions = toSelectOptions(pendingService.findAll());

            settings = propSettingService.findAll();
            expressionUtils.setVariable("pendingMap", pendingOptions);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Worktime download(Worktime wt) throws Exception {
        List<MaterialPropertyValue> remotePropSettings = materialPropertyValueQueryPort.query(wt);
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
