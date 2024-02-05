/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.service;

import com.advantech.dao.BasicDAOImpl;
import com.advantech.dao.WorktimeLevelSettingDAO;
import com.advantech.model.WorktimeLevelSetting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional
public class WorktimeLevelSettingService extends BasicServiceImpl<Integer, WorktimeLevelSetting> {
    
    @Autowired
    private WorktimeLevelSettingDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }
    
    public List<WorktimeLevelSetting> findByWorktime(int worktimeId) {
        return dao.findByWorktime(worktimeId);
    }
}
