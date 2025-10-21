/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service;

import com.advantech.dao.*;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.LabelVariable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wei.Cheng
 */
@Service
@Transactional
public class LabelVariableService extends BasicServiceImpl<Integer, LabelVariable> {

    @Autowired
    private LabelVariableDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    @Autowired
    private LabelVariableGroupService labelVariableGroupService;

    public List<LabelVariable> findAll(PageInfo info) {
        return dao.findAll(info);
    }

    public LabelVariable findByLabelVariableName(String labelVariableName) {
        return dao.findByLabelVariableName(labelVariableName);
    }

    public List<LabelVariable> findByLabelVariableGroup(int labelVariableGroupId) {
        return dao.findByLabelVariableGroup(labelVariableGroupId);
    }

    public List<LabelVariable> findByParent(Integer parent_id) {
        LabelVariable f = this.findByPrimaryKey(parent_id);
        return f == null ? new ArrayList() : new ArrayList(f.getLabelVariablesForChildId());
    }

    public int addSub(int parentLabelVariableId, List<Integer> subLabelVariableId) {
        LabelVariable f = this.findByPrimaryKey(parentLabelVariableId);
        Set<LabelVariable> subLabelVariables = f.getLabelVariablesForChildId();
        for (Integer id : subLabelVariableId) {
            subLabelVariables.add(this.findByPrimaryKey(id));
        }
        this.update(f);
        return 1;
    }

    public int deleteSub(int parentLabelVariableId, List<Integer> subLabelVariableId) {
        LabelVariable f = this.findByPrimaryKey(parentLabelVariableId);
        Set<LabelVariable> subLabelVariables = f.getLabelVariablesForChildId();

        Iterator it = subLabelVariables.iterator();

        while (it.hasNext()) {
            LabelVariable subLabelVariable = (LabelVariable) it.next();
            if (subLabelVariableId.contains(subLabelVariable.getId())) {
                it.remove();
            }
        }

        return 1;
    }

    public int delete(int id) {
        LabelVariable labelVariable = this.findByPrimaryKey(id);
        return dao.delete(labelVariable);
    }

}
