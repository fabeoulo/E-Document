/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao.db2;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model2.WorktimeColumnGroupM4f;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Justin.Yeh
 */
@Repository
public class WorktimeColumnGroupM4fDAO extends BasicDAOImpl<Integer, WorktimeColumnGroupM4f> {

    public List<WorktimeColumnGroupM4f> findAll(PageInfo info) {
        return getByPaginateInfo(info);
    }

    public WorktimeColumnGroupM4f findByUnit(int obj_id) {
        Criteria criteria = createEntityCriteria();
        criteria.createAlias("unit", "u");
        criteria.add(Restrictions.eq("u.id", obj_id));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return (WorktimeColumnGroupM4f) criteria.uniqueResult();
    }

}
