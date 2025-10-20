/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service.db2;

import com.advantech.dao.db2.*;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model2.CartonLabelM4f;
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
public class CartonLabelM4fService extends BasicServiceImpl<Integer, CartonLabelM4f> {

    @Autowired
    private CartonLabelM4fDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<CartonLabelM4f> findAll(PageInfo info) {
        return dao.findAll(info);
    }

    public int delete(int id) {
        CartonLabelM4f cartonlabel = this.findByPrimaryKey(id);
        return dao.delete(cartonlabel);
    }

}
