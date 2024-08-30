/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service.db2;

import com.advantech.dao.db2.BasicDAOImpl;
import com.advantech.dao.db2.WorktimeMaterialPropertyUploadSettingM4fDAO;
import com.advantech.model.db2.WorktimeMaterialPropertyUploadSettingM4f;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional("tx2")
public class WorktimeMaterialPropertyUploadSettingM4fService extends BasicServiceImpl<Integer, WorktimeMaterialPropertyUploadSettingM4f> {

    @Autowired
    private WorktimeMaterialPropertyUploadSettingM4fDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

}
