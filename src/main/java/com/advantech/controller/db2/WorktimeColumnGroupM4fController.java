/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.controller.db2;

import com.advantech.controller.*;
import static com.advantech.helper.JqGridResponseUtils.toJqGridResponse;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.User;
import com.advantech.jqgrid.JqGridResponse;
import com.advantech.model2.UnitM4f;
import com.advantech.model2.WorktimeColumnGroupM4f;
import com.advantech.service.db2.WorktimeColumnGroupM4fService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Justin.Yeh
 */
@Controller
@RequestMapping(value = "/WorktimeColumnGroupM4f")
public class WorktimeColumnGroupM4fController extends CrudController<WorktimeColumnGroupM4f> {

    @Autowired
    private WorktimeColumnGroupM4fService worktimeColumnGroupService;

    @ResponseBody
    @RequestMapping(value = SELECT_URL, method = {RequestMethod.GET})
    @Override
    protected JqGridResponse read(PageInfo info) {
        return toJqGridResponse(worktimeColumnGroupService.findAll(info), info);
    }

    @ResponseBody
    @RequestMapping(value = INSERT_URL, method = {RequestMethod.POST})
    @Override
    protected ResponseEntity insert(WorktimeColumnGroupM4f pojo, BindingResult bindingResult) {
        String modifyMessage;
        if (isWorktimeColumnGroupExistsInUnit(pojo)) {
            modifyMessage = "Setting is already exists.";
        } else {
            modifyMessage = worktimeColumnGroupService.insert(pojo) == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
        }
        return serverResponse(modifyMessage);
    }

    @ResponseBody
    @RequestMapping(value = UPDATE_URL, method = {RequestMethod.POST})
    @Override
    protected ResponseEntity update(WorktimeColumnGroupM4f pojo, BindingResult bindingResult) {
        String modifyMessage;
        if (isWorktimeColumnGroupExistsInUnit(pojo)) {
            modifyMessage = "Setting is already exists.";
        } else {
            modifyMessage = worktimeColumnGroupService.update(pojo) == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
        }
        return serverResponse(modifyMessage);
    }

    @ResponseBody
    @RequestMapping(value = DELETE_URL, method = {RequestMethod.POST})
    @Override
    protected ResponseEntity delete(int id) {
        String modifyMessage = worktimeColumnGroupService.delete(id) == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
        return serverResponse(modifyMessage);
    }

    @ResponseBody
    @RequestMapping(value = "/byUnit", method = {RequestMethod.GET})
    protected WorktimeColumnGroupM4f getUnitColumnName() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int unit = user.getUnit().getId();
        WorktimeColumnGroupM4f w = worktimeColumnGroupService.findByUnit(unit);
        return w == null ? new WorktimeColumnGroupM4f() : w;
    }

    private boolean isWorktimeColumnGroupExistsInUnit(WorktimeColumnGroupM4f w) {
        UnitM4f u = w.getUnit();
        WorktimeColumnGroupM4f existRule = worktimeColumnGroupService.findByUnit(u.getId());
        return existRule != null && existRule.getId() != w.getId();
    }

}
