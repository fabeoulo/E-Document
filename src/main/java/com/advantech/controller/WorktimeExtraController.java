/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.controller;

import static com.advantech.helper.JqGridResponseUtils.toJqGridResponse;
import com.advantech.jqgrid.JqGridResponse;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.Worktime;
import com.advantech.model.WorktimeAutouploadSetting;
import com.advantech.model.WorktimeExtra;
import com.advantech.service.WorktimeAutouploadSettingService;
import com.advantech.service.WorktimeExtraService;
import com.advantech.service.WorktimeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/WorktimeExtra")
public class WorktimeExtraController extends CrudController<WorktimeExtra> {

    @Autowired
    private WorktimeExtraService worktimeExtraService;

    @Autowired
    private WorktimeService worktimeService;

    @Autowired
    private WorktimeAutouploadSettingService worktimeAutouploadSettingService;

    @ResponseBody
    @RequestMapping(value = SELECT_URL, method = {RequestMethod.GET})
    @Override
    protected JqGridResponse read(PageInfo info) {
        return toJqGridResponse(worktimeExtraService.findAll(info), info);
    }

    @ResponseBody
    @RequestMapping(value = INSERT_URL, method = {RequestMethod.POST})
    @Override
    protected ResponseEntity insert(WorktimeExtra pojo, BindingResult bindingResult) {
        setPojo(pojo);

        String modifyMessage = worktimeExtraService.insert(pojo) == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
        return serverResponse(modifyMessage);
    }

    private WorktimeExtra setPojo(WorktimeExtra pojo) {
        PageInfo info = new PageInfo();
        info.setSearchField("id");
        info.setSearchOper("eq");
        info.setSearchString(String.valueOf(pojo.getWorktime().getId()));

        List<Worktime> w = worktimeService.findWithFullRelation(info);
        String wc = w.isEmpty() ? "" : w.get(0).getWorkCenter().getName();
        pojo.setWorkCenter(wc);

        WorktimeAutouploadSetting set = worktimeAutouploadSettingService.findByPrimaryKey(pojo.getWorktimeAutouploadSetting().getId());
        pojo.setProcess(set.getColumnUnit());

        return pojo;
    }

    @ResponseBody
    @RequestMapping(value = UPDATE_URL, method = {RequestMethod.POST})
    @Override
    protected ResponseEntity update(WorktimeExtra pojo, BindingResult bindingResult) {
        setPojo(pojo);

        String modifyMessage = worktimeExtraService.update(pojo) == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
        return serverResponse(modifyMessage);
    }

    @ResponseBody
    @RequestMapping(value = DELETE_URL, method = {RequestMethod.POST})
    @Override
    protected ResponseEntity delete(int id) {
        String modifyMessage = worktimeExtraService.delete(id) == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
        return serverResponse(modifyMessage);
    }

}
