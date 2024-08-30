/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service.db2;

import com.advantech.dao.db2.*;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.PreAssyM4f;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional("tx2")
public class PreAssyM4fService extends BasicServiceImpl<Integer, PreAssyM4f> {

    @Autowired
    private PreAssyM4fDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<PreAssyM4f> findAll(PageInfo info) {
        return dao.findAll(info);
    }

    public PreAssyM4f findByFlowName(String flowName) {
        return dao.findByFlowName(flowName);
    }

    public int delete(int id) {
        PreAssyM4f preAssy = this.findByPrimaryKey(id);
        return dao.delete(preAssy);
    }

}
