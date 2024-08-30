/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service;

import com.advantech.dao.WorktimeAuditDAO;
import com.advantech.model.Worktime;
import com.advantech.webservice.port.FlowUploadPort;
import com.advantech.webservice.port.MaterialPropertyUploadPort;
import com.advantech.webservice.port.ModelResponsorUploadPort;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wei.Cheng
 */
@Service
@Transactional
public class WorktimeUploadMesService {

    @Autowired
    private WorktimeAuditService worktimeAuditService;

    @Autowired
    private ModelResponsorUploadPort responsorUploadPort;

    @Autowired
    private FlowUploadPort flowUploadPort;

    @Autowired
    private MaterialPropertyUploadPort materialPropertyUploadPort;

    @Value("${WORKTIME.UPLOAD.INSERT: true}")
    private boolean isInserted;

    @Value("${WORKTIME.UPLOAD.UPDATE: true}")
    private boolean isUpdated;

    @Value("${WORKTIME.UPLOAD.DELETE: true}")
    private boolean isDeleted;

//    @Value("${WORKTIME.UPLOAD.SOP: false}")
//    private boolean isUploadSop;
    @Value("${WORKTIME.UPLOAD.RESPONSOR: true}")
    private boolean isUploadResponsor;

    @Value("${WORKTIME.UPLOAD.FLOW: true}")
    private boolean isUploadFlow;

    @Value("${WORKTIME.UPLOAD.MATPROPERTY: true}")
    private boolean isUploadMatProp;

    public void portParamInit() throws Exception {
        if (isUploadMatProp) {
            materialPropertyUploadPort.initSetting();
        }
    }

    public void insert(Worktime w) throws Exception {
        if (isInserted) {
            if (isUploadResponsor) {
                try {
                    responsorUploadPort.insert(w);
                } catch (Exception e) {
                    throw new Exception("機種負責人新增至MES失敗<br />" + e.getMessage());
                }
            }
            if (isUploadFlow) {
                try {
                    flowUploadPort.insert(w);
                } catch (Exception e) {
                    throw new Exception("徒程新增至MES失敗<br />" + e.getMessage());
                }
            }
            if (isUploadMatProp) {
                try {
                    materialPropertyUploadPort.insert(w);
                } catch (Exception e) {
                    throw new Exception("料號屬性值新增至MES失敗<br />" + e.getMessage());
                }
            }
        }
    }

    public void update(Worktime w) throws Exception {
        if (isUpdated) {
            Worktime rowLastStatus = worktimeAuditService.findLastStatusBeforeUpdate(w.getId());
            if (isUploadResponsor && isModelResponsorChanged(rowLastStatus, w)) {
                try {
                    responsorUploadPort.update(w);
                } catch (Exception e) {
                    throw new Exception("機種負責人更新至MES失敗<br />" + e.getMessage());
                }
            }

            if (isUploadFlow && isFlowChanged(rowLastStatus, w)) {
                try {
                    flowUploadPort.update(w);
                } catch (Exception e) {
                    throw new Exception("徒程更新至MES失敗<br />" + e.getMessage());
                }
            }

            if (isUploadMatProp && isMatPropertyChanged(rowLastStatus, w)) {
                try {
                    materialPropertyUploadPort.update(w);
                } catch (Exception e) {
                    throw new Exception("料號屬性值更新至MES失敗<br />" + e.getMessage());
                }
            }
        }
    }

    private boolean isModelNameChanged(Worktime prev, Worktime current) {
        return !prev.getModelName().equals(current.getModelName());
    }

    private boolean isSopChanged(Worktime prev, Worktime current) {
        return isModelNameChanged(prev, current)
                || !isEquals(prev.getAssyPackingSop(), current.getAssyPackingSop())
                || !isEquals(prev.getTestSop(), current.getTestSop());
    }

    //Revision entity relation object are lasy loading.
    private boolean isModelResponsorChanged(Worktime prev, Worktime current) {
        return isModelNameChanged(prev, current)
                || !isEquals(prev.getUserBySpeOwnerId(), current.getUserBySpeOwnerId())
                || !isEquals(prev.getUserByEeOwnerId(), current.getUserByEeOwnerId())
                || !isEquals(prev.getUserByQcOwnerId(), current.getUserByQcOwnerId())
                || !isEquals(prev.getUserByMpmOwnerId(), current.getUserByMpmOwnerId());
    }

    //Revision entity relation object are lasy loading.
    private boolean isFlowChanged(Worktime prev, Worktime current) {
        return isModelNameChanged(prev, current)
                || !isEquals(prev.getPreAssy(), current.getPreAssy())
                || !isEquals(prev.getFlowByBabFlowId(), current.getFlowByBabFlowId())
                || !isEquals(prev.getFlowByTestFlowId(), current.getFlowByTestFlowId())
                || !isEquals(prev.getFlowByPackingFlowId(), current.getFlowByPackingFlowId());
    }

    private boolean isMatPropertyChanged(Worktime prev, Worktime current) {
        boolean b = isModelNameChanged(prev, current)
                || !isEquals(prev.getPending(), current.getPending())
                || !isEquals(prev.getPendingTime(), current.getPendingTime())
                || !isEquals(prev.getBurnIn(), current.getBurnIn())
                || !isEquals(prev.getBiTime(), current.getBiTime())
                || !isEquals(prev.getBiTemperature(), current.getBiTemperature())
                || !isEquals(prev.getKeypartA(), current.getKeypartA())
                || !isEquals(prev.getKeypartB(), current.getKeypartB())
                || !isEquals(prev.getCe(), current.getCe())
                || !isEquals(prev.getCe(), current.getCe())
                || !isEquals(prev.getUl(), current.getUl())
                || !isEquals(prev.getRohs(), current.getRohs())
                || !isEquals(prev.getWeee(), current.getWeee())
                || !isEquals(prev.getMadeInTaiwan(), current.getMadeInTaiwan())
                || !isEquals(prev.getFcc(), current.getFcc())
                || !isEquals(prev.getEac(), current.getEac())
                || !isEquals(prev.getKc(), current.getKc())
                || !isEquals(prev.getNsInOneCollectionBox(), current.getNsInOneCollectionBox())
                || !isEquals(prev.getLabelInformation(), current.getLabelInformation())
                || !isEquals(prev.getWeight(), current.getWeight())
                || !isEquals(prev.getWeightAff(), current.getWeightAff())
                || !isEquals(prev.getTolerance(), current.getTolerance())
                || !isEquals(prev.getPartNoAttributeMaintain(), current.getPartNoAttributeMaintain())
                || !isEquals(prev.getPartLink(), current.getPartLink());

        return b;
    }

    private <T extends Comparable> boolean isEquals(T o1, T o2) {
        return ObjectUtils.compare(o1, o2) == 0;
    }

    public void delete(Worktime w) throws Exception {
        if (isDeleted) {
            if (isUploadResponsor) {
                try {
                    responsorUploadPort.delete(w);
                } catch (Exception e) {
                    throw new Exception("機種負責人刪除至MES失敗<br />" + e.getMessage());
                }
            }

            if (isUploadFlow) {
                try {
                    flowUploadPort.delete(w);
                } catch (Exception e) {
                    throw new Exception("徒程刪除至MES失敗<br />" + e.getMessage());
                }
            }

            if (isUploadMatProp) {
                try {
                    materialPropertyUploadPort.delete(w);
                } catch (Exception e) {
                    throw new Exception("料號屬性值刪除至MES失敗<br />" + e.getMessage());
                }
            }
        }
    }
}
