/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.controller;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.BusinessGroup;
import com.advantech.model.Cobot;
import com.advantech.model.Floor;
import com.advantech.model.Flow;
import com.advantech.model.FlowGroup;
import com.advantech.model.Pending;
import com.advantech.model.PreAssy;
import com.advantech.model.Remark;
import com.advantech.model.Type;
import com.advantech.model.Unit;
import com.advantech.model.User;
import com.advantech.model.UserNotification;
import com.advantech.model.UserProfile;
import com.advantech.model.WorkCenter;
import com.advantech.model.Worktime;
import com.advantech.model.WorktimeAutouploadSetting;
import com.advantech.service.BusinessGroupService;
import com.advantech.service.CobotService;
import com.advantech.service.FloorService;
import com.advantech.service.FlowGroupService;
import com.advantech.service.FlowService;
import com.advantech.service.UserService;
import com.advantech.service.PendingService;
import com.advantech.service.PreAssyService;
import com.advantech.service.RemarkService;
import com.advantech.service.TypeService;
import com.advantech.service.UnitService;
import com.advantech.service.UserNotificationService;
import com.advantech.service.UserProfileService;
import com.advantech.service.WorkCenterService;
import com.advantech.service.WorktimeAutouploadSettingService;
import com.advantech.service.WorktimeService;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Wei.Cheng 供大表的lazy loading欄位編輯與查詢
 */
@Controller
@RequestMapping(value = "/SelectOption")
public class WorktimeSelectOptionController {

    @Autowired
    private FloorService floorService;

    @Autowired
    private UserService userService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private FlowService flowService;

    @Autowired
    private FlowGroupService flowGroupService;

    @Autowired
    private PreAssyService preAssyService;

    @Autowired
    private PendingService pendingService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private BusinessGroupService businessGroupService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserNotificationService userNotificationService;

    @Autowired
    private RemarkService remarkService;

    @Autowired
    private WorkCenterService workCenterService;

    @Autowired
    private CobotService cobotService;

    @Autowired
    private WorktimeAutouploadSettingService worktimeAutouploadSettingService;

    @Autowired
    private WorktimeService worktimeService;

    @ResponseBody
    @RequestMapping(value = "/floor", method = {RequestMethod.POST})
    protected List<Floor> getFloorOption() {
        return floorService.findByPrimaryKeys(4, 5);
    }

    @ResponseBody
    @RequestMapping(value = "/user-floor", method = {RequestMethod.POST})
    protected List<Floor> getUserFloorOption() {
        return floorService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/user/{unitName}", method = {RequestMethod.POST})
    protected List<User> getUserOption(@PathVariable(value = "unitName") final String unitName) {
        return userService.findByUnitName(unitName);
    }

    @ResponseBody
    @RequestMapping(value = "/type", method = {RequestMethod.POST})
    protected List<Type> getTypeOption() {
        return typeService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/flow", method = {RequestMethod.POST})
    public List<Flow> getAllFlowOption() {
        return flowService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/flow/{flowGroupId}", method = {RequestMethod.POST})
    protected List<Flow> getFlowOption(@PathVariable(value = "flowGroupId") final int flowGroupId) {
        return flowService.findByFlowGroup(flowGroupId);
    }

    @ResponseBody
    @RequestMapping(value = "/flow-byParent/{parentFlowId}", method = {RequestMethod.POST})
    protected List<Flow> getFlowOptionByParent(@PathVariable(value = "parentFlowId") final int parentFlowId) {
        return flowService.findByParent(parentFlowId);
    }

    @ResponseBody
    @RequestMapping(value = "/flowGroup", method = {RequestMethod.POST})
    protected List<FlowGroup> getFlowGroupOption() {
        return flowGroupService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/preAssy", method = {RequestMethod.POST})
    protected List<PreAssy> getPreAssyOption() {
        return preAssyService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/pending", method = {RequestMethod.POST})
    protected List<Pending> getPendingOption() {
        return pendingService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/unit", method = {RequestMethod.POST})
    protected List<Unit> getUnitOption() {
        return unitService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/businessGroup", method = {RequestMethod.POST})
    protected List<BusinessGroup> getBusinessGroupOption() {
        return businessGroupService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/workCenter", method = {RequestMethod.POST})
    protected List<WorkCenter> getWorkCenterOption() {
        return workCenterService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/workCenter/{businessGroupId}", method = {RequestMethod.POST})
    protected List<WorkCenter> getWorkCenterOption(@PathVariable(value = "businessGroupId") final int businessGroupId) {
        return workCenterService.findByBusinessGroup(businessGroupId);
    }

    @ResponseBody
    @RequestMapping(value = "/userProfiles", method = {RequestMethod.POST})
    protected List<UserProfile> getUserProfileOption(HttpServletRequest request) {
        List<UserProfile> l = userProfileService.findAll();
        if (!request.isUserInRole("ROLE_ADMIN")) {
            l.removeIf(item -> "ADMIN".equals(item.getName()));
        }
        return l;
    }

    @ResponseBody
    @RequestMapping(value = "/userUserNotifications", method = {RequestMethod.POST})
    protected List<UserNotification> getUserNotificationOption() {
        return userNotificationService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/remark", method = {RequestMethod.POST})
    protected List<Remark> getRemarkOption() {
        return remarkService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/cobots", method = {RequestMethod.POST})
    protected List<Cobot> getCobotOption() throws Exception {
        return cobotService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/worktimeStations", method = {RequestMethod.POST})
    protected List<Map> getWorktimeStationOption() {
        PageInfo info = new PageInfo();
        info.setRows(-1);
        info.setSearchField("stationId");
        info.setSearchOper("ne");
        info.setSearchString(null);

        List<WorktimeAutouploadSetting> l = worktimeAutouploadSettingService.findAll(info);

        List<Map> lr = l.stream()
                .map(i -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", i.getId());
                    map.put("name", i.getColumnName());
                    return map;
                })
                .sorted(Comparator.comparing(
                        m -> (String) m.get("name"),
                        String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        return lr;
    }

    @ResponseBody
    @RequestMapping(value = "/modelNames", method = {RequestMethod.POST})
    protected List<Map> getModelNameOption() {

        List<Worktime> l = worktimeService.findAll();

        List<Map> lr = l.stream()
                .map(i -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", i.getId());
                    map.put("name", i.getModelName());
                    return map;
                })
                .sorted(Comparator.comparing(
                        m -> (String) m.get("name"),
                        String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        return lr;
    }
}
