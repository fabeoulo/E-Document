/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service.db2;

import com.advantech.dao.db2.*;
import com.advantech.model2.FloorM4f;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional("tx2")
public class FloorM4fService extends BasicServiceImpl<Integer, FloorM4f> {

    @Autowired
    private FloorM4fDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

}
