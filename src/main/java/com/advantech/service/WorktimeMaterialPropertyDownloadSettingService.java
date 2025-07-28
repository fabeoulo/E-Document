/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service;

import com.advantech.dao.BasicDAOImpl;
import com.advantech.dao.WorktimeMaterialPropertyDownloadSettingDAO;
import com.advantech.model.WorktimeMaterialPropertyDownloadSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional("tx1")
public class WorktimeMaterialPropertyDownloadSettingService extends BasicServiceImpl<Integer, WorktimeMaterialPropertyDownloadSetting> {

    @Autowired
    private WorktimeMaterialPropertyDownloadSettingDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

}
