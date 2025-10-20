/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao.db2;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model2.FlowGroupM4f;
import com.advantech.model2.FlowM4f;
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
public class FlowM4fDAO extends BasicDAOImpl<Integer, FlowM4f> {

    public List<FlowM4f> findAll(PageInfo info) {
        return super.getByPaginateInfo(info);
    }

    public List<FlowM4f> findByFlowGroup(FlowGroupM4f flowGroup) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("flowGroup", flowGroup));
        criteria.addOrder(Order.asc("name"));
        return criteria.list();
    }

    public FlowM4f findByFlowName(String flowName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", flowName));
        return (FlowM4f) criteria.uniqueResult();
    }

}
