/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao;

import com.advantech.helper.PageInfo;
import com.advantech.model.Pending;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wei.Cheng
 */
@Repository
public class PendingDAO implements BasicDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PaginateDAO paginateDAO;

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Collection findAll() {
        return currentSession().createQuery("from Pending").list();
    }

    public List<Pending> findAll(PageInfo info) {
        return paginateDAO.findAll(this.currentSession(), Pending.class, info);
    }

    @Override
    public Object findByPrimaryKey(Object obj_id) {
        return currentSession().load(Pending.class, (int) obj_id);
    }

    @Override
    public int insert(Object obj) {
        this.currentSession().save(obj);
        return 1;
    }

    @Override
    public int update(Object obj) {
        this.currentSession().update(obj);
        return 1;
    }

    @Override
    public int delete(Object pojo) {
        this.currentSession().delete(pojo);
        return 1;
    }

}