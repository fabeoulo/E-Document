/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service.db2;

import com.advantech.dao.db2.BasicDAOImpl;
import com.advantech.dao.db2.BusinessGroupM4fDAO;
import com.advantech.model.db2.BusinessGroupM4f;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional(value = "tx2")
public class BusinessGroupM4fService extends BasicServiceImpl<Integer, BusinessGroupM4f> {

    @Autowired
    private BusinessGroupM4fDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return dao;
    }

    public Map<String, BusinessGroupM4f> getMapByNotDisable() {
        return dao.findAll().stream().filter(bg -> bg.getDisable() != 1)
                .collect(Collectors.toMap(BusinessGroupM4f::getName, Function.identity()));

    }
}
