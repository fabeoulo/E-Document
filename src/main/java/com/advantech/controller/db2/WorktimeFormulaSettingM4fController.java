/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.controller.db2;

import com.advantech.model.db2.WorktimeFormulaSettingM4f;
import com.advantech.service.db2.WorktimeFormulaSettingM4fService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Justin.Yeh
 */
@Controller
@RequestMapping(value = "/WorktimeFormulaSettingM4f")
public class WorktimeFormulaSettingM4fController {

    @Autowired
    private WorktimeFormulaSettingM4fService worktimeFormulaSettingService;

    @ResponseBody
    @RequestMapping(value = "/find/{worktimeId}", method = {RequestMethod.GET})
    protected WorktimeFormulaSettingM4f read(@PathVariable(value = "worktimeId") int worktimeId) {
        List<WorktimeFormulaSettingM4f> l = worktimeFormulaSettingService.findByWorktime(worktimeId);
        return l.isEmpty() ? null : l.get(0);
    }

}
