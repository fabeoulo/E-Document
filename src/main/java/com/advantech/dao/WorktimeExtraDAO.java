/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.dao;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.WorktimeExtra;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Justin.Yeh
 */
@Repository
public class WorktimeExtraDAO extends BasicDAOImpl<Integer, WorktimeExtra> {

    public List<WorktimeExtra> findAll(PageInfo info) {
        return super.getByPaginateInfo(info);
    }

    public List<WorktimeExtra> findWithFullRelation(PageInfo info) {
        String[] fetchField = {
            "worktime", "worktimeAutouploadSetting"
        };

        Criteria criteria = createEntityCriteria();
        for (String field : fetchField) {
            criteria.setFetchMode(field, FetchMode.JOIN);
        }

        List l = getByPaginateInfo(criteria, info);
        return l;
    }
}
