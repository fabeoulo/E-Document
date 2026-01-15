/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service;

import com.advantech.dao.BasicDAOImpl;
import com.advantech.dao.WorktimeAutouploadSettingDAO;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.WorktimeAutouploadSetting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wei.Cheng
 */
@Service
@Transactional
public class WorktimeAutouploadSettingService extends BasicServiceImpl<Integer, WorktimeAutouploadSetting> {

    @Autowired
    private WorktimeAutouploadSettingDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<WorktimeAutouploadSetting> findAll(PageInfo info) {
        return dao.findAll(info);
    }
}
