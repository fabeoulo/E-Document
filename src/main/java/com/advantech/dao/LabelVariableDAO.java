/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.LabelVariable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wei.Cheng
 */
@Repository
public class LabelVariableDAO extends BasicDAOImpl<Integer, LabelVariable> {

    public List<LabelVariable> findAll(PageInfo info) {
        return super.getByPaginateInfo(info);
    }

    public List<LabelVariable> findByLabelVariableGroup(int labelVariableGroupId) {
        Criteria criteria = createEntityCriteria();
        criteria.createAlias("labelVariableGroup", "group");
        criteria.add(Restrictions.eq("group.id", labelVariableGroupId));
        criteria.addOrder(Order.asc("name"));
        return criteria.list();
    }

    public LabelVariable findByLabelVariableName(String labelVariableName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", labelVariableName)).setMaxResults(1);
        return (LabelVariable) criteria.uniqueResult();
    }

}
