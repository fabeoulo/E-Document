/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice.download.db2;

import com.advantech.model2.WorktimeM4f;
import com.advantech.webservice.Factory;
import com.advantech.webservice.port.MtdTestIntegrityQueryPort;
import com.advantech.webservice.unmarshallclass.MtdTestIntegrity;
import static com.google.common.collect.Lists.newArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Justin.Yeh
 */
@Component
public class MtdTestIntegrityM4fDownload extends BasicM4fDownload<WorktimeM4f> {

    private static final Logger logger = LoggerFactory.getLogger(MtdTestIntegrityM4fDownload.class);

    @Autowired
    private MtdTestIntegrityQueryPort mtdTestIntegrityQueryPort;

    @Override
    public WorktimeM4f download(WorktimeM4f wt) throws Exception {
        List<MtdTestIntegrity> mtdTestIntegritys = mtdTestIntegrityQueryPort.queryM(wt, Factory.TWM9);

        MtdTestIntegrity t1TestIntegrity = mtdTestIntegritys.stream().filter(t -> "T1".equals(t.getStationName())).findFirst().orElse(null);
        MtdTestIntegrity t2TestIntegrity = mtdTestIntegritys.stream().filter(t -> "T2".equals(t.getStationName())).findFirst().orElse(null);
        List<Integer> t1TestQty = t1TestIntegrity == null ? newArrayList(0, 0) : newArrayList(t1TestIntegrity.getStateCnt(), t1TestIntegrity.getItemCnt());
        List<Integer> t2TestQty = t2TestIntegrity == null ? newArrayList(0, 0) : newArrayList(t2TestIntegrity.getStateCnt(), t2TestIntegrity.getItemCnt());

        wt.setT1StatusQty(t1TestQty.get(0));
        wt.setT1ItemsQty(t1TestQty.get(1));
        wt.setT2StatusQty(t2TestQty.get(0));
        wt.setT2ItemsQty(t2TestQty.get(1));

        return wt;
    }
}
