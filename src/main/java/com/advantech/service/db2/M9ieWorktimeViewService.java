/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service.db2;

import com.advantech.dao.db2.*;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.M9ieWorktimeView;
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
public class M9ieWorktimeViewService extends BasicServiceImpl<Integer, M9ieWorktimeView> {

    @Autowired
    private M9ieWorktimeViewDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<M9ieWorktimeView> findAll(PageInfo info) {
        return dao.findAll(info);
    }

    public List<M9ieWorktimeView> findByModelNames(String... modelName) {
        return dao.findByModel(modelName);
    }
}
