/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.quartzJob;

import com.advantech.webapi.CalculatorApiClient;
import com.advantech.model.Worktime;
import com.advantech.service.WorktimeService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Justin.Yeh
 */
@Component
public class SyncPreAssyModuleQty {

    @Autowired
    private CalculatorApiClient calculatorApiClient;

    @Autowired
    private WorktimeService worktimeService;

    public void execute() {
        Map<String, String>[] maps = calculatorApiClient.getPreAssyModule();
        Map<String, Integer> modelModules = Arrays.stream(maps)
                .filter(map -> map.get("preAssyModule").startsWith("(前置)"))
                .collect(Collectors.groupingBy(
                        map -> map.get("modelName"),
                        Collectors.summingInt(map -> 1)
                ));

        List<Worktime> l = worktimeService.findAll();
        l = l.stream().filter(w -> {
            int qty = modelModules.getOrDefault(w.getModelName(), 0);
            if (w.getPreAssyModuleQty() != qty) {
                w.setPreAssyModuleQty(qty);
                w.setReasonCode("");
                w.setWorktimeModReason("Sync preassy module qty.");

                return true;
            }
            return false;
        }).collect(Collectors.toList());
        worktimeService.update(l);
    }
}
