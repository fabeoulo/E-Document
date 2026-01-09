/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.service;

import com.advantech.dao.BasicDAOImpl;
import com.advantech.dao.SuggestionWorktimeHistoryDAO;
import com.advantech.model.SuggestionWorktimeHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional
public class SuggestionWorktimeHistoryService extends BasicServiceImpl<Integer, SuggestionWorktimeHistory> {

    @Autowired
    private SuggestionWorktimeHistoryDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

}
