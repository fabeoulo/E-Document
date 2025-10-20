/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao.db2;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model2.PreAssyM4f;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Justin.Yeh
 */
@Repository
public class PreAssyM4fDAO extends BasicDAOImpl<Integer, PreAssyM4f> {

    public List<PreAssyM4f> findAll(PageInfo info) {
        return super.getByPaginateInfo(info);
    }

    public PreAssyM4f findByFlowName(String flowName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", flowName));
        return (PreAssyM4f) criteria.uniqueResult();
    }
}
