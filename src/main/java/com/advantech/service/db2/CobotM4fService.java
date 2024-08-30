/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.service.db2;

import com.advantech.dao.db2.BasicDAOImpl;
import com.advantech.dao.db2.CobotM4fDAO;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.CobotM4f;
import com.advantech.model.db2.WorktimeM4f;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional("tx2")
public class CobotM4fService extends BasicServiceImpl<Integer, CobotM4f> {

    @Autowired
    private CobotM4fDAO dao;

    @Autowired
    private WorktimeM4fService worktimeService;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<CobotM4f> findAll(PageInfo info) {
        return dao.findAll(info);
    }

    @Override
    public int update(CobotM4f pojo) {
        //Prevent update many to many relationship when pojo's column changed
        CobotM4f dataInDb = this.findByPrimaryKey(pojo.getId());
        dataInDb.setWorktimeMinutes(pojo.getWorktimeMinutes());
        dataInDb.setWorktimeSeconds(pojo.getWorktimeSeconds());
        dao.update(dataInDb);
        
        //Reset worktime when cobot's worktime is changed
        List<WorktimeM4f> l = worktimeService.findAll();
        for (WorktimeM4f w : l) {
            worktimeService.setCobotWorktime(w);
        }
        worktimeService.merge(l);
        return 1;
    }
}
