/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao.db2;

import com.advantech.model.db2.WorktimeFormulaSettingM4f;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wei.Cheng
 */
@Repository
public class WorktimeFormulaSettingM4fDAO extends BasicDAOImpl<Integer, WorktimeFormulaSettingM4f> {

    public List<WorktimeFormulaSettingM4f> findByWorktime(int worktimeId) {
        Criteria c = createEntityCriteria();
        c.add(Restrictions.eq("worktime.id", worktimeId));
        return c.list();
    }

    public List<WorktimeFormulaSettingM4f> findWithWorktime() {
        Criteria c = createEntityCriteria();
        c.setFetchMode("worktime", FetchMode.JOIN);
        return c.list();
    }

    public int merge(WorktimeFormulaSettingM4f pojo) {
        getSession().merge(pojo);
        return 1;
    }

}
