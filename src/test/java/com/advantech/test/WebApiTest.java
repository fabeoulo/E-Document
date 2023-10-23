/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.test;

import com.advantech.api.CalculatorApiClient;
import com.advantech.helper.HibernateObjectPrinter;
import com.advantech.model.Worktime;
import com.advantech.service.WorktimeService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author Justin.Yeh
 */
@WebAppConfiguration
@ContextConfiguration(locations = {
    "classpath:servlet-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class WebApiTest {

    @Autowired
    private CalculatorApiClient calculatorApiClient;

    @Autowired
    private WorktimeService worktimeService;

//    @Test
//    @Transactional
//    @Rollback(false)
    public void testCalculatorApiClient() {
        Map<String, String>[] maps = calculatorApiClient.getPreAssyModule();
        Map<String, Integer> modelModules = Arrays.stream(maps)
                .filter(map -> map.get("preAssyModule").startsWith("(前置)"))
                .collect(Collectors.groupingBy(map -> map.get("modelName"),
                        Collectors.summingInt(map -> 1)));
//        HibernateObjectPrinter.print(modelModules);

//        List<Worktime> l = worktimeService.findAll();
//        l = l.stream().filter(w -> {
//            int qty = modelModules.getOrDefault(w.getModelName(), 0);
//            if (w.getPreAssyModuleQty() != qty) {
//                w.setPreAssyModuleQty(qty);
//                w.setReasonCode("");
//                w.setWorktimeModReason("Sync preassy module qty.");
//
//                return true;
//            }
//            return false;
//        }).collect(Collectors.toList());
//        worktimeService.update(l);
    }

}
