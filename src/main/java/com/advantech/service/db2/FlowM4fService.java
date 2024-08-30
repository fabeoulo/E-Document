/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service.db2;

import com.advantech.dao.db2.*;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.FlowGroupM4f;
import com.advantech.model.db2.FlowM4f;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wei.Cheng
 */
@Service
@Transactional("tx2")
public class FlowM4fService extends BasicServiceImpl<Integer, FlowM4f> {

    @Autowired
    private FlowM4fDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    @Autowired
    private FlowGroupM4fService flowGroupService;

    public List<FlowM4f> findAll(PageInfo info) {
        return dao.findAll(info);
    }

    public FlowM4f findByFlowName(String flowName) {
        return dao.findByFlowName(flowName);
    }

    public List<FlowM4f> findByFlowGroup(int flowGroupId) {
        FlowGroupM4f fg = flowGroupService.findByPrimaryKey(flowGroupId);
        return dao.findByFlowGroup(fg);
    }

    public List<FlowM4f> findByParent(Integer parent_id) {
        List l = new ArrayList();
        FlowM4f f = this.findByPrimaryKey(parent_id);
        l.addAll(f.getFlowsForTestFlowId());
        return l;
    }

    public List<FlowM4f> findFlowWithSub() {
        List<FlowM4f> l = this.findAll();
        for (FlowM4f f : l) {
            Hibernate.initialize(f.getFlowsForTestFlowId());
        }
        return l;
    }

    public int addSub(int parentFlowId, List<Integer> subFlowId) {
        FlowM4f f = this.findByPrimaryKey(parentFlowId);
        Set<FlowM4f> subFlows = f.getFlowsForTestFlowId();
        for (Integer id : subFlowId) {
            subFlows.add(this.findByPrimaryKey(id));
        }
        this.update(f);
        return 1;
    }

    public int deleteSub(int parentFlowId, List<Integer> subFlowId) {
        FlowM4f f = this.findByPrimaryKey(parentFlowId);
        Set<FlowM4f> subFlows = f.getFlowsForTestFlowId();

        Iterator it = subFlows.iterator();

        while (it.hasNext()) {
            FlowM4f subFlow = (FlowM4f) it.next();
            if (subFlowId.contains(subFlow.getId())) {
                it.remove();
            }
        }

        return 1;
    }
    
    public int delete(int id) {
        FlowM4f flow = this.findByPrimaryKey(id);
        return dao.delete(flow);
    }

}
