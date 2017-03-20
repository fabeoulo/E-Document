/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service;

import com.advantech.dao.*;
import com.advantech.helper.PageInfo;
import java.util.Collection;
import org.hibernate.SessionFactory;

/**
 *
 * @author Wei.Cheng
 */
public class SheetIEService implements BasicService {

    private final SheetIEDAO sheetIEDAO;

    public SheetIEService() {
        sheetIEDAO = new SheetIEDAO();
    }

    @Override
    public Collection findAll() {
        return sheetIEDAO.findAll();
    }

    public Collection findAll(PageInfo info) {
        return sheetIEDAO.findAll(info);
    }

    @Override
    public Object findByPrimaryKey(Object obj_id) {
        return sheetIEDAO.findByPrimaryKey(obj_id);
    }

    public String[] getColumnName() {
        return sheetIEDAO.getColumnName();
    }

    @Override
    public int insert(Object obj) {
        return sheetIEDAO.insert(obj);
    }

    @Override
    public int update(Object obj) {
        return sheetIEDAO.update(obj);
    }

    @Override
    public int delete(Object pojo) {
        return sheetIEDAO.delete(pojo);
    }

}