/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.M9ieWorktimeView;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Justin.Yeh
 */
@Repository
public class M9ieWorktimeViewDAO extends BasicDAOImpl<Integer, M9ieWorktimeView> {

    public List<M9ieWorktimeView> findAll(PageInfo info) {
        return super.getByPaginateInfo(info);
    }

    public List<M9ieWorktimeView> findByModel(String... modelName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.in("modelName", modelName));
        return criteria.list();
    }

    @Override
    public int insert(M9ieWorktimeView pojo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(M9ieWorktimeView pojo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(M9ieWorktimeView pojo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
