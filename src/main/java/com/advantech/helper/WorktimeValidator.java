/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.helper;

import com.advantech.model.Worktime;
import com.advantech.model.WorktimeFormulaSetting;
import com.advantech.service.WorktimeFormulaSettingService;
import com.advantech.service.WorktimeService;
import com.advantech.webservice.port.StandardWorkReasonQueryPort;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Wei.Cheng
 */
@Component
public class WorktimeValidator {

    @Autowired
    private WorktimeService worktimeService;

    @Autowired
    private WorktimeFormulaSettingService settingService;

    @Autowired
    private StandardWorkReasonQueryPort reasonPort;

    public void checkProductionWtChanged(Worktime w) {
        List<WorktimeFormulaSetting> settings = w.getWorktimeFormulaSettings();
        WorktimeFormulaSetting setting = settings.stream().filter(p -> p.getWorktime().getId() == w.getId()).findFirst().orElse(null);
        Worktime existW = worktimeService.findByPrimaryKey(w.getId());
        checkProductionWt(w, existW, setting);
    }

    public void checkProductionWtChanged(List<Worktime> l) {
        List<WorktimeFormulaSetting> settings = settingService.findAll();
        List<Worktime> worktimes = worktimeService.findAll();

        l.forEach((w) -> {
            WorktimeFormulaSetting setting = settings.stream().filter(p -> p.getWorktime().getId() == w.getId()).findFirst().orElse(null);
            Worktime existW = worktimes.stream().filter(p -> p.getId() == w.getId()).findFirst().orElse(null);
            checkProductionWt(w, existW, setting);
        });
    }

    private void checkProductionWt(Worktime currentW, Worktime existW, WorktimeFormulaSetting setting) {

        boolean fieldChangeFlag = this.isProductWtRelativeFieldChanged(currentW, existW);

        checkState(setting != null, "Can't find fit setting in input param");
        checkState(existW != null);

        boolean flag;

        //Column calculable
        if (setting.getProductionWt() == 1) {
            currentW.setDefaultProductWt();//none use. need to update.

            BigDecimal b1 = currentW.getProductionWt();
            BigDecimal b2 = existW.getProductionWt();

            flag = !b1.equals(b2);
        } else {
            flag = fieldChangeFlag;
        }
    }

    private boolean isProductWtRelativeFieldChanged(Worktime currentW, Worktime existW) {
        boolean flag = Objects.equals(currentW.getProductionWt(), existW.getProductionWt())
                && Objects.equals(currentW.getSetupTime(), existW.getSetupTime())
                && Objects.equals(currentW.getCleanPanel(), existW.getCleanPanel())
                && Objects.equals(currentW.getAssy(), existW.getAssy())
                && Objects.equals(currentW.getT1(), existW.getT1())
                && Objects.equals(currentW.getT2(), existW.getT2())
                && Objects.equals(currentW.getT3(), existW.getT3())
                && Objects.equals(currentW.getPacking(), existW.getPacking())
                && Objects.equals(currentW.getUpBiRi(), existW.getUpBiRi())
                && Objects.equals(currentW.getDownBiRi(), existW.getDownBiRi())
                && Objects.equals(currentW.getBiCost(), existW.getBiCost());
        return flag == false;
    }

    public void checkModelNameExists(Worktime worktime) {
        Worktime existW = worktimeService.findByModel(worktime.getModelName());
        boolean existFlag;
        if (worktime.getId() == 0) {
            existFlag = existW != null;
        } else {
            existFlag = existW != null && existW.getId() != worktime.getId();
        }
        checkArgument(existFlag == false, "This modelName &lt;" + worktime.getModelName() + "&gt; is already exist.");
    }

    public void checkModelNameExists(List<Worktime> worktimes) throws Exception {
        Map<String, Integer> modelMap = new HashMap();
        List<Worktime> allWorktime = worktimeService.findAll();
        allWorktime.forEach((w) -> {
            modelMap.put(w.getModelName(), w.getId());
        });

        worktimes.forEach((w) -> {
            boolean existFlag;
            Integer worktimeId = modelMap.get(w.getModelName());
            if (w.getId() == 0) {
                existFlag = worktimeId != null;
            } else {
                existFlag = worktimeId != null && worktimeId != w.getId();
            }
            checkArgument(existFlag == false, "This modelName &lt;" + w.getModelName() + "&gt; is already exist.");
        });
    }

}
