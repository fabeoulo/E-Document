/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service;

import com.advantech.dao.*;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.OutLabel;
import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Eric.Hong
 */
@Service
@Transactional
public class OutLabelService extends BasicServiceImpl<Integer, OutLabel> {

    @Autowired
    private OutLabelDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<OutLabel> findAll(PageInfo info) {
        return dao.findAll(info);
    }

    public int delete(int id) {
        OutLabel outlabel = this.findByPrimaryKey(id);
        return dao.delete(outlabel);
    }

}
