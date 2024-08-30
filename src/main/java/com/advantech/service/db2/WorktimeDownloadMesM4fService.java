/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.service.db2;

import com.advantech.model.db2.WorktimeM4f;
import com.advantech.webservice.download.FlowM4fDownload;
import com.advantech.webservice.download.MaterialPropertyValueM4fDownload;
import com.advantech.webservice.download.ModelResponsorM4fDownload;
import com.advantech.webservice.download.MtdTestIntegrityM4fDownload;
import com.advantech.webservice.download.StandardWorkTimeM4fDownload;
import com.google.common.collect.Lists;
import java.util.List;
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
    private WorktimeM4fService instance;

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

    public void insertByModels(List<WorktimeM4f> l) {
        try {
            for (WorktimeM4f wm4 : l) {
                logger.info("Processing: " + wm4.getModelName() + " in thread: " + Thread.currentThread().getName());

                modelResponsorM4fDownload.download(wm4);
                flowM4fDownload.download(wm4);
                materialPropertyValueM4fDownload.download(wm4);
                mtdTestIntegrityM4fDownload.download(wm4);
                standardWorkTimeM4fDownload.download(wm4);

                instance.setNotNullFieldDefault(wm4);

                instance.insertByMesModels(Lists.newArrayList(wm4));
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
