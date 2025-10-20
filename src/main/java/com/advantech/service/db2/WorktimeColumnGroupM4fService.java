/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service.db2;

import com.advantech.dao.db2.BasicDAOImpl;
import com.advantech.dao.db2.WorktimeColumnGroupM4fDAO;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model2.WorktimeColumnGroupM4f;
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
public class WorktimeColumnGroupM4fService extends BasicServiceImpl<Integer, WorktimeColumnGroupM4f> {

    @Autowired
    private WorktimeColumnGroupM4fDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<WorktimeColumnGroupM4f> findAll(PageInfo info) {
        return dao.findAll(info);
    }

    public WorktimeColumnGroupM4f findByUnit(int obj_id) {
        return dao.findByUnit(obj_id);
    }

    public int delete(int id) {
        WorktimeColumnGroupM4f w = this.findByPrimaryKey(id);
        return dao.delete(w);
    }

}
