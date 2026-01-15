/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.service;

import com.advantech.dao.BasicDAOImpl;
import com.advantech.dao.WorktimeExtraDAO;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.WorktimeExtra;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional
public class WorktimeExtraService extends BasicServiceImpl<Integer, WorktimeExtra> {

    @Autowired
    private WorktimeExtraDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<WorktimeExtra> findAll(PageInfo info) {
        return dao.findAll(info);
    }

    public List<WorktimeExtra> findWithFullRelation(PageInfo info) {
        List<WorktimeExtra> result = dao.findWithFullRelation(info);
        return result;
    }

    public int delete(int id) {
        WorktimeExtra pojo = this.findByPrimaryKey(id);
        return dao.delete(pojo);
    }
}
