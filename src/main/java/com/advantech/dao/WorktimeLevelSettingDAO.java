/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.dao;

import com.advantech.model.WorktimeLevelSetting;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Justin.Yeh
 */
@Repository
public class WorktimeLevelSettingDAO extends BasicDAOImpl<Integer, WorktimeLevelSetting> {
    
    public List<WorktimeLevelSetting> findByWorktime(int worktimeId) {
        Criteria c = createEntityCriteria();
        c.add(Restrictions.eq("worktime.id", worktimeId));
        return c.list();
    }

    public List<WorktimeLevelSetting> findWithWorktime() {
        Criteria c = createEntityCriteria();
        c.setFetchMode("worktime", FetchMode.JOIN);
        return c.list();
    }
    
    @Override
    public int merge(WorktimeLevelSetting pojo) {
        getSession().merge(pojo);
        return 1;
    }
}
