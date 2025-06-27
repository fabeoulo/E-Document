/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service;

import com.advantech.dao.BasicDAOImpl;
import com.advantech.dao.WorktimeAutodownloadSettingDAO;
import com.advantech.model.WorktimeAutodownloadSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional
public class WorktimeAutodownloadSettingService extends BasicServiceImpl<Integer, WorktimeAutodownloadSetting> {

    @Autowired
    private WorktimeAutodownloadSettingDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

}
