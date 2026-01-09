/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.service.db3;

import com.advantech.dao.BasicDAOImpl;
import com.advantech.dao.db3.WorktimeReviseddownHistoryDAO;
import com.advantech.model3.WorktimeReviseddownHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Justin.Yeh
 */
@Service
public class WorktimeReviseddownHistoryService extends BasicServiceImpl<Integer, WorktimeReviseddownHistory> {

    @Autowired
    private WorktimeReviseddownHistoryDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }
}
