/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service;

import com.advantech.dao.*;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.CartonLabel;
import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wei.Cheng
 */
@Service
@Transactional
public class CartonLabelService extends BasicServiceImpl<Integer, CartonLabel> {

    @Autowired
    private CartonLabelDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<CartonLabel> findAll(PageInfo info) {
        return dao.findAll(info);
    }

    public int delete(int id) {
        CartonLabel cartonlabel = this.findByPrimaryKey(id);
        return dao.delete(cartonlabel);
    }

}
