/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.service.db2;

import com.advantech.model.db2.BusinessGroupM4f;
import com.advantech.model.db2.M9ieWorktimeView;
import com.advantech.model.db2.WorktimeM4f;
import com.advantech.webservice.download.FlowM4fDownload;
import com.advantech.webservice.download.MaterialPropertyValueM4fDownload;
import com.advantech.webservice.download.ModelResponsorM4fDownload;
import com.advantech.webservice.download.MtdTestIntegrityM4fDownload;
import com.advantech.webservice.download.StandardWorkTimeM4fDownload;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional("tx2")
public class WorktimeDownloadMesM4fService {

    private static final Logger logger = LoggerFactory.getLogger(WorktimeDownloadMesM4fService.class);

    @Autowired
    private WorktimeM4fService worktimeM4fService;

    @Autowired
    private M9ieWorktimeViewService m9ieWorktimeViewService;

    @Autowired
    private BusinessGroupM4fService businessGroupM4fService;

    @Autowired
    private FlowM4fDownload flowM4fDownload;

    @Autowired
    private ModelResponsorM4fDownload modelResponsorM4fDownload;

    @Autowired
    private MtdTestIntegrityM4fDownload mtdTestIntegrityM4fDownload;

    @Autowired
    private MaterialPropertyValueM4fDownload materialPropertyValueM4fDownload;

    @Autowired
    private StandardWorkTimeM4fDownload standardWorkTimeM4fDownload;

    public void portParamInit() {
        flowM4fDownload.initOptions();
        modelResponsorM4fDownload.initOptions();
        materialPropertyValueM4fDownload.initOptions();
        standardWorkTimeM4fDownload.initOptions();

    }

    // for test now
    public void saveOrUpdateByModels(List<String> modelNames) {
        if (modelNames.isEmpty()) {
            return;
        }
        // SQL limit to 2000 params(modelNames).
        List<M9ieWorktimeView> views;
        String[] arr = modelNames.stream().limit(2000).toArray(String[]::new);
        views = m9ieWorktimeViewService.findByModelNames(arr);

        Map<String, List> listMap = filterByViewAndSetBg(views);

        List<WorktimeM4f> wtIn = listMap.get("wtIn");
        List<WorktimeM4f> wtNew = listMap.get("wtNew");

        this.portParamInit();
        logger.info("Begin add WorktimeM4f : " + wtNew.size() + " datas.");
        this.insertMesDL(wtNew);
        logger.info("Begin merge WorktimeM4f : " + wtIn.size() + " datas.");
        this.mergeMesDLWithFormulaSetting(wtIn);

    }

    public Map<String, List> filterByViewAndSetBg(List<M9ieWorktimeView> views) {

        Map<String, M9ieWorktimeView> m9Map = views.stream().collect(Collectors.toMap(v -> v.getModelName(), v -> v, (a, b) -> b));
        Map<String, WorktimeM4f> wtMap = worktimeM4fService.findAll().stream().collect(Collectors.toMap(wt -> wt.getModelName(), wt -> wt));
        Map<String, BusinessGroupM4f> buMap = businessGroupM4fService.findAll().stream().collect(Collectors.toMap(bu -> bu.getWorkCenter(), bu -> bu));

        logger.info("M9ieWorktimeView size : " + m9Map.size() + " WorktimeM4f size : " + wtMap.size());

        List<WorktimeM4f> wtIn = new ArrayList<>();
        List<WorktimeM4f> wtNew = new ArrayList<>();
        m9Map.entrySet().stream().forEach(m9En -> {

            String modelName = m9En.getKey();
            M9ieWorktimeView m9wt = m9En.getValue();

            WorktimeM4f w;
            if (wtMap.containsKey(modelName)) {
                w = wtMap.get(modelName);
                wtIn.add(w);
            } else {
                w = new WorktimeM4f();
                w.setModelName(modelName);
                wtNew.add(w);
            }

            String wc = m9wt.getWorkCenter();
            w.setWorkCenter(wc);
            w.setBusinessGroup(buMap.getOrDefault(wc, null));
        });

        List<WorktimeM4f> wtRemove = wtMap.entrySet().stream().filter(wtEn -> !m9Map.containsKey(wtEn.getKey()))
                .map(wtEn -> wtEn.getValue())
                .collect(Collectors.toList());

        Map<String, List> resultMap = new HashMap<>();
        resultMap.put("wtIn", wtIn);
        resultMap.put("wtNew", wtNew);
        resultMap.put("wtRemove", wtRemove);
        return resultMap;
    }

    public void insertMesDL(List<WorktimeM4f> l) {
        for (WorktimeM4f wm4 : l) {
            try {
                logger.info("InsertDLByModels: " + wm4.getModelName() + " in thread: " + Thread.currentThread().getName());

                downloadFromMes(wm4);
                worktimeM4fService.setNotNullFieldDefault(wm4);
                worktimeM4fService.insertByMesDL(wm4);
            } catch (Exception e) {
                logger.error(wm4.getModelName() + " : " + e.getMessage());
            }
        }
    }

    private void mergeMesDLWithFormulaSetting(List<WorktimeM4f> l) {
        for (WorktimeM4f wm4 : l) {
            try {
                logger.info("MergeDLByModels: " + wm4.getModelName() + " in thread: " + Thread.currentThread().getName());

                downloadFromMes(wm4);
                worktimeM4fService.setNotNullFieldDefault(wm4);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        worktimeM4fService.mergeWithoutUpload(l);
    }

    private void downloadFromMes(WorktimeM4f wm4) throws Exception {
        materialPropertyValueM4fDownload.download(wm4);
        modelResponsorM4fDownload.download(wm4);
        flowM4fDownload.download(wm4);
        mtdTestIntegrityM4fDownload.download(wm4);
        standardWorkTimeM4fDownload.download(wm4);
    }
}
