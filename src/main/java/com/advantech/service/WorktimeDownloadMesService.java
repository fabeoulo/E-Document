/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.service;

import com.advantech.model.BusinessGroup;
import com.advantech.model.M9ieWorktimeView;
import com.advantech.model.Worktime;
import com.advantech.webservice.download.StandardWorkTimeDownload;
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
@Service(value = "WorktimeDownloadMesService")
@Transactional("tx1")
public class WorktimeDownloadMesService {

    private static final Logger logger = LoggerFactory.getLogger(WorktimeDownloadMesService.class);

    @Autowired
    private WorktimeService worktimeService;

    @Autowired
    private M9ieWorktimeViewService m9ieWorktimeViewService;

//    @Autowired
//    private BusinessGroupService businessGroupService;
//
//    @Autowired
//    private FlowM4fDownload flowM4fDownload;
//
//    @Autowired
//    private ModelResponsorM4fDownload modelResponsorM4fDownload;
//
//    @Autowired
//    private MtdTestIntegrityM4fDownload mtdTestIntegrityM4fDownload;
//
//    @Autowired
//    private MaterialPropertyValueM4fDownload materialPropertyValueM4fDownload;
//    
    @Autowired
    private StandardWorkTimeDownload standardWorkTimeDownload;

    public void portParamInit() {
//        flowM4fDownload.initOptions();
//        modelResponsorM4fDownload.initOptions();
//        materialPropertyValueM4fDownload.initOptions();
        standardWorkTimeDownload.initOptions();

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

        List<Worktime> wtIn = listMap.get("wtIn");
        List<Worktime> wtNew = listMap.get("wtNew");

        this.portParamInit();
//        logger.info("Begin add Worktime : " + wtNew.size() + " datas.");
//        this.insertMesDL(wtNew);
        logger.info("Begin merge Worktime : " + wtIn.size() + " datas.");
        this.mergeMesDLWithFormulaSetting(wtIn);

    }

    public Map<String, List> filterByViewAndSetBg(List<M9ieWorktimeView> views) {

        Map<String, M9ieWorktimeView> m9Map = views.stream().collect(Collectors.toMap(v -> v.getModelName(), v -> v, (a, b) -> b));
        Map<String, Worktime> wtMap = worktimeService.findAllWithFormula().stream().collect(Collectors.toMap(wt -> wt.getModelName(), wt -> wt));
//        Map<String, BusinessGroup> buMap = businessGroupService.findAll().stream().collect(Collectors.toMap(bu -> bu.getWorkCenter(), bu -> bu));

        logger.info("M9ieWorktimeView size : " + m9Map.size() + " Worktime size : " + wtMap.size());

        List<Worktime> wtIn = new ArrayList<>();
        List<Worktime> wtNew = new ArrayList<>();
        m9Map.entrySet().stream().forEach(m9En -> {

            String modelName = m9En.getKey();
            M9ieWorktimeView m9wt = m9En.getValue();

            Worktime w;
            if (wtMap.containsKey(modelName)) {
                w = wtMap.get(modelName);
                wtIn.add(w);
            } else {
                w = new Worktime();
                w.setModelName(modelName);
                wtNew.add(w);
            }

            String wc = m9wt.getWorkCenter();
            w.setWorkCenter(wc);
//            w.setBusinessGroup(buMap.getOrDefault(wc, null));
        });

        List<Worktime> wtRemove = wtMap.entrySet().stream().filter(wtEn -> !m9Map.containsKey(wtEn.getKey()))
                .map(wtEn -> wtEn.getValue())
                .collect(Collectors.toList());

        Map<String, List> resultMap = new HashMap<>();
        resultMap.put("wtIn", wtIn);
        resultMap.put("wtNew", wtNew);
        resultMap.put("wtRemove", wtRemove);
        return resultMap;
    }

//    public void insertMesDL(List<Worktime> l) {
//        for (Worktime wm4 : l) {
//            try {
//                logger.info("InsertDLByModels: " + wm4.getModelName() + " in thread: " + Thread.currentThread().getName());
//
//                downloadFromMes(wm4);
//                worktimeService.setNotNullFieldDefault(wm4);
//                worktimeService.insertByMesDL(wm4);
//            } catch (Exception e) {
//                logger.error(wm4.getModelName() + " : " + e.getMessage());
//            }
//        }
//    }
    private void mergeMesDLWithFormulaSetting(List<Worktime> l) {
        for (Worktime wm4 : l) {
            try {
                logger.info("MergeDLByModels: " + wm4.getModelName() + " in thread: " + Thread.currentThread().getName());

                downloadFromMes(wm4);
//                worktimeService.setNotNullFieldDefault(wm4);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        worktimeService.mergeWithoutUpload(l);
    }

    private void downloadFromMes(Worktime wm4) throws Exception {
//        materialPropertyValueM4fDownload.download(wm4);
//        modelResponsorM4fDownload.download(wm4);
//        flowM4fDownload.download(wm4);
//        mtdTestIntegrityM4fDownload.download(wm4);
        standardWorkTimeDownload.download(wm4);
    }
}
