/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service.db2;

import com.advantech.dao.db2.BasicDAOImpl;
import com.advantech.dao.db2.WorktimeFormulaSettingM4fDAO;
import com.advantech.model.db2.WorktimeFormulaSettingM4f;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional(value = "tx2")
public class WorktimeFormulaSettingM4fService extends BasicServiceImpl<Integer, WorktimeFormulaSettingM4f> {

    @Autowired
    private WorktimeFormulaSettingM4fDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<WorktimeFormulaSettingM4f> findByWorktime(int worktimeId) {
        return dao.findByWorktime(worktimeId);
    }

}
