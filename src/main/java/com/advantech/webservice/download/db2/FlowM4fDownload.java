/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice.download.db2;

import com.advantech.model2.FlowGroupM4f;
import com.advantech.model2.FlowM4f;
import com.advantech.model2.PreAssyM4f;
import com.advantech.model2.WorktimeM4f;
import com.advantech.service.db2.FlowM4fService;
import com.advantech.service.db2.PreAssyM4fService;
import com.advantech.webservice.Factory;
import com.advantech.webservice.port.MaterialFlowQueryPort;
import com.advantech.webservice.root.Section;
import com.advantech.webservice.unmarshallclass.MaterialFlow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Justin.Yeh
 */
@Component
public class FlowM4fDownload extends BasicM4fDownload<WorktimeM4f> {

    private static final Logger logger = LoggerFactory.getLogger(FlowM4fDownload.class);

    @Autowired
    private MaterialFlowQueryPort materialFlowQueryPort;

    @Autowired
    private PreAssyM4fService preAssyService;

    @Autowired
    private FlowM4fService flowService;

    private Map<String, PreAssyM4f> preAssyOptions;

    private Map<String, FlowM4f> flowOptions;

    public void initOptions() {
        try {
            preAssyOptions = super.toSelectOptions(preAssyService.findAll());
            flowOptions = super.toSelectOptions(flowService.findAll());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public WorktimeM4f download(WorktimeM4f wt) throws Exception {
        List<MaterialFlow> mesFlows = materialFlowQueryPort.queryM(wt, Factory.TWM9);
        Map<String, String> errorFields = new HashMap();

        for (Section section : Section.values()) {
            try {
                MaterialFlow mf = mesFlows.stream().filter(materialFlow -> materialFlow.getUnitNo().equals(section.getCode())).findFirst().orElse(null);
                if (mf == null) {
                    continue;
                }

                String flowName = mf.getFlowRuleName();
                if (super.isNullOrEmpty(flowName)) {
                    continue;
                }

                switch (section) {
                    case PREASSY:
                        PreAssyM4f preAssy = preAssyOptions.get(flowName);
                        if (preAssy == null) {
                            preAssy = new PreAssyM4f();
                            preAssy.setName(flowName);
                            preAssyService.insert(preAssy);
                            preAssyOptions.put(flowName, preAssy);
                        }
                        wt.setPreAssy(preAssy);
                        break;
                    case BAB:
                        FlowM4f babFlow = flowOptions.get(flowName);
                        if (babFlow == null) {
                            babFlow = new FlowM4f();
                            babFlow.setName(flowName);
                            babFlow.setFlowGroup(new FlowGroupM4f(1));
                            flowService.insert(babFlow);
                            flowOptions.put(flowName, babFlow);
                        }
                        wt.setFlowByBabFlowId(babFlow);
                        break;
                    case TEST:
                        FlowM4f testFlow = flowOptions.get(flowName);
                        if (testFlow == null) {
                            testFlow = new FlowM4f();
                            testFlow.setName(flowName);
                            testFlow.setFlowGroup(new FlowGroupM4f(3));
                            flowService.insert(testFlow);
                            flowOptions.put(flowName, testFlow);
                        }
                        wt.setFlowByTestFlowId(testFlow);
                        break;
                    case PACKAGE:
                        FlowM4f pkgFlow = flowOptions.get(flowName);
                        if (pkgFlow == null) {
                            pkgFlow = new FlowM4f();
                            pkgFlow.setName(flowName);
                            pkgFlow.setFlowGroup(new FlowGroupM4f(2));
                            flowService.insert(pkgFlow);
                            flowOptions.put(flowName, pkgFlow);
                        }
                        wt.setFlowByPackingFlowId(pkgFlow);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                errorFields.put(section + "_Flow", e.getMessage());
            }
        }

        if (!errorFields.isEmpty()) {
            throw new Exception(wt.getModelName() + " 徒程從MES讀取失敗: " + errorFields.toString());
        }
        saveFlowRef(wt);

        return wt;
    }

    private void saveFlowRef(WorktimeM4f wt) {
        if (wt.getFlowByBabFlowId() != null && wt.getFlowByTestFlowId() != null) {
            int babFlowId = wt.getFlowByBabFlowId().getId();
            int testFlowId = wt.getFlowByTestFlowId().getId();

            List<FlowM4f> testFlows = flowService.findByParent(babFlowId);
            if (testFlows.stream().noneMatch(f -> f.getId() == testFlowId)) {
                List<Integer> addSubIds = new ArrayList();
                addSubIds.add(testFlowId);
                flowService.addSub(babFlowId, addSubIds);
            }
        }
    }
}
