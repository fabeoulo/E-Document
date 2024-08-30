/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service.db2;

import com.advantech.dao.db2.*;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.TypeM4f;
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
@Transactional("tx2")
public class TypeM4fService extends BasicServiceImpl<Integer, TypeM4f> {

    @Autowired
    private TypeM4fDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<TypeM4f> findAll(PageInfo info) {
        return dao.findAll(info);
    }

    public int delete(int id) {
        TypeM4f type = this.findByPrimaryKey(id);
        return dao.delete(type);
    }

}
