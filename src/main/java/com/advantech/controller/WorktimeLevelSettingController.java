/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.controller;

import com.advantech.model.WorktimeLevelSetting;
import com.advantech.service.WorktimeLevelSettingService;
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
@RequestMapping(value = "/WorktimeLevelSetting")
public class WorktimeLevelSettingController {
    
    @Autowired
    private WorktimeLevelSettingService worktimeLevelSettingService;

    @ResponseBody
    @RequestMapping(value = "/find/{worktimeId}", method = {RequestMethod.GET})
    protected WorktimeLevelSetting read(@PathVariable(value = "worktimeId") int worktimeId) {
        List<WorktimeLevelSetting> l = worktimeLevelSettingService.findByWorktime(worktimeId);
        return l.isEmpty() ? null : l.get(0);
    }
}
