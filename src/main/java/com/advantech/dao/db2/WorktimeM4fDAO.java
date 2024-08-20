/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.dao.db2;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.WorktimeM4f;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Justin.Yeh
 */
@Repository
public class WorktimeM4fDAO extends BasicDAOImpl<Integer, WorktimeM4f> {

    public List<WorktimeM4f> findAll(PageInfo info) {
        Criteria criteria = createEntityCriteria()
                .setFetchMode("cobots", FetchMode.SELECT)
                .createAlias("bwFields", "bwFields", JoinType.LEFT_OUTER_JOIN);
        List l = getByPaginateInfo(criteria, info);
        return l;
    }

    public List<WorktimeM4f> findByModel(String... modelName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.in("modelName", modelName));
        return criteria.list();
    }

    public List<WorktimeM4f> findWithFullRelation(PageInfo info) {
        String[] fetchField = {
            "type", "businessGroup", "floor", "pending", "preAssy",
            "flowByBabFlowId", "flowByPackingFlowId", "flowByTestFlowId",
            "userBySpeOwnerId", "userByEeOwnerId", "userByQcOwnerId", "userByMpmOwnerId",
            "labelOuterId", "labelCartonId"
        };

        Criteria criteria = createEntityCriteria();
        for (String field : fetchField) {
            criteria.setFetchMode(field, FetchMode.JOIN);
        }

        criteria.setFetchMode("cobots", FetchMode.SELECT);
//        criteria.createAlias("cobots", "cobots", JoinType.LEFT_OUTER_JOIN);

        String fetchField_c = "bwFields";
        criteria.createAlias(fetchField_c, fetchField_c, JoinType.LEFT_OUTER_JOIN);

        List l = getByPaginateInfo(criteria, info);
        return l;
    }

    public int merge(WorktimeM4f pojo) {
        getSession().merge(pojo);
        return 1;
    }

    public int saveOrUpdate(WorktimeM4f pojo) {
        getSession().saveOrUpdate(pojo);
        return 1;
    }

}
