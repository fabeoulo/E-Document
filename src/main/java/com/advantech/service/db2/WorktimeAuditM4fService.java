/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service.db2;

import com.advantech.service.*;
import com.advantech.dao.AuditAction;
import com.advantech.dao.db2.WorktimeAuditM4fDAO;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.WorktimeM4f;
import java.util.Date;
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
public class WorktimeAuditM4fService implements AuditAction<WorktimeM4f, Integer> {

    @Autowired
    private WorktimeAuditM4fDAO auditDAO;

    @Override
    public List findAll() {
        return auditDAO.findAll();
    }

    public List findAll(PageInfo info) {
        return auditDAO.findAll(info);
    }

    @Override
    public List findByPrimaryKey(Integer id) {
        return auditDAO.findByPrimaryKey(id);
    }

    @Override
    public Object findByPrimaryKeyAndVersion(Integer id, int version) {
        return auditDAO.findByPrimaryKeyAndVersion(id, version);
    }

    @Override
    public List<Number> findRevisions(Integer id) {
        return auditDAO.findRevisions(id);
    }

    @Override
    public List forEntityAtRevision(int version) {
        return auditDAO.forEntityAtRevision(version);
    }

    public List findModifiedAtRevision(int version) {
        return auditDAO.findModifiedAtRevision(version);
    }

    public List findByDate(PageInfo info, Date startDate, Date endDate) {
        return this.findByDate(null, info, startDate, endDate);
    }

    public List findByDate(Integer id, PageInfo info, Date startDate, Date endDate) {
        return auditDAO.findByDate(id, info, startDate, endDate);
    }

    public Number findLastRevisions() {
        return auditDAO.findLastRevisions(null);
    }

    public Number findLastRevisions(Integer id) {
        return auditDAO.findLastRevisions(id);
    }

    public boolean isFieldChangedAtLastRevision(Integer id, String[] fieldNames) {
        return auditDAO.isFieldChangedAtLastRevision(id, fieldNames);
    }

    public WorktimeM4f findLastStatusBeforeUpdate(Integer id) {
        return auditDAO.findLastStatusBeforeUpdate(id);
    }

    public List findByFieldChangedInDate(Integer id, List<String> fieldNames, Date startDate, Date endDate) {
        return auditDAO.findByFieldChangedInDate(id, fieldNames, startDate, endDate);
    }

}
